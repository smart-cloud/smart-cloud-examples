/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.support.gateway.service.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.exception.BusinessException;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import io.github.smart.cloud.utility.HttpUtil;
import io.github.smart.cloud.utility.spring.SpringContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.smartframework.cloud.examples.api.ac.core.constants.ApiMetaConstants;
import org.smartframework.cloud.examples.api.ac.core.util.ApiMetaUtil;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqDTO;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 接口元数据（签名、加解密、权限等）处理
 *
 * @author collin
 * @date 2020/04/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMetaRpcService {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final Redisson redisson;
    private final Environment environment;
    private static final long RETRY_FETCH_SERVICE_INSTANCE_DURATION = 6000L;

    public void notifyFetch(NotifyFetchReqDTO req) throws IOException, InterruptedException {
        String serviceName = req.getServiceName();
        RLock lock = redisson.getLock(RedisKeyHelper.getApiMetaLockKey(serviceName));
        boolean requireLock = false;
        try {
            requireLock = lock.tryLock();
            if (!requireLock) {
                return;
            }

            fetchAndUpdateApiMetas(serviceName);
        } finally {
            if (requireLock) {
                lock.unlock();
            }
        }
    }

    /**
     * 获取并更新api mate信息
     *
     * @param serviceName
     * @throws IOException
     */
    private void fetchAndUpdateApiMetas(String serviceName) throws IOException, InterruptedException {
        String currentServiceName = environment.getProperty(ApiMetaConstants.SERVICE_NAME_KEY);
        ApiMetaFetchRespVO apiMetaFetch = null;
        if (StringUtils.equals(currentServiceName, serviceName)) {
            apiMetaFetch = ApiMetaUtil.collectApiMetas();
        } else {
            String url = getFetchUrl(serviceName);
            Response<ApiMetaFetchRespVO> apiMetaFetchRespVO = fetchApiMeta(url);

            if (!ResponseUtil.isSuccess(apiMetaFetchRespVO)) {
                throw new BusinessException(GatewayReturnCodes.FETCH_APIMETA_FAIL);
            }
            apiMetaFetch = apiMetaFetchRespVO.getBody();
        }

        if (apiMetaFetch == null || MapUtils.isEmpty(apiMetaFetch.getApiAccessMap())) {
            return;
        }

        // redis持久化
        Map<String, ApiAccessMetaCache> allApiAccess = new HashMap<>(8);
        apiMetaFetch.getApiAccessMap().forEach((urlMethod, apiAccess) -> allApiAccess.put(RedisKeyHelper.getApiMetaHashKey(urlMethod), new ApiAccessMetaCache(apiAccess)));

        redisTemplate.opsForHash().putAll(RedisKeyHelper.getApiMetaKey(), allApiAccess);
    }

    /**
     * 获取正在启动服务的url
     *
     * @param serviceName
     * @return
     */
    private String getFetchUrl(String serviceName) throws InterruptedException {
        List<ServiceInstance> serviceInstances = getServiceInstances(serviceName);
        Assert.notEmpty(serviceInstances, String.format("service[%s] not registered", serviceName));

        ServiceInstance serviceInstance = serviceInstances.get(serviceInstances.size() - 1);
        return String.format("http://%s:%s%s", serviceInstance.getHost(), serviceInstance.getPort(), ApiMetaConstants.FETCH_URL);
    }

    private List<ServiceInstance> getServiceInstances(String serviceName) throws InterruptedException {
        DiscoveryClient discoveryClient = SpringContextUtil.getBean(DiscoveryClient.class);
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        long startTime = System.currentTimeMillis();
        while (CollectionUtils.isEmpty(serviceInstances)) {
            TimeUnit.MILLISECONDS.sleep(100);
            serviceInstances = discoveryClient.getInstances(serviceName);
            if (System.currentTimeMillis() - startTime > RETRY_FETCH_SERVICE_INSTANCE_DURATION) {
                log.error("retry fetch service instance duration {}", RETRY_FETCH_SERVICE_INSTANCE_DURATION);
                break;
            }
        }

        return serviceInstances;
    }

    /**
     * 以get方式请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Response<ApiMetaFetchRespVO> fetchApiMeta(String url) throws IOException {
        return HttpUtil.get(url, null, new TypeReference<Response<ApiMetaFetchRespVO>>() {
        }, null);
    }

}