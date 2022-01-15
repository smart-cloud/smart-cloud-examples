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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.api.ac.core.constants.ApiMetaConstants;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqDTO;
import org.smartframework.cloud.exception.BusinessException;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.utility.HttpUtil;
import org.smartframework.cloud.utility.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 接口元数据（签名、加解密、权限等）处理
 *
 * @author collin
 * @date 2020/04/28
 */
@Service
@Slf4j
public class ApiMetaRpcService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void notifyFetch(NotifyFetchReqDTO req) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String url = getFetchUrl(req.getServiceName());
//        Response<ApiMetaFetchRespVO> apiMetaFetchRespVO = fetchApiMeta(url);
//
//        if (!RespUtil.isSuccess(apiMetaFetchRespVO)) {
//            throw new BusinessException(GatewayReturnCodes.FETCH_APIMETA_FAIL);
//        }
//        ApiMetaFetchRespVO apiMetaFetch = apiMetaFetchRespVO.getBody();
//        if (apiMetaFetch == null || MapUtils.isEmpty(apiMetaFetch.getApiAccessMap())) {
//            return;
//        }
//        // redis持久化
//        apiMetaFetch.getApiAccessMap().forEach((urlMethod, apiAccess) ->
//                redisTemplate.opsForHash().put(RedisKeyHelper.getApiMetaKey(), RedisKeyHelper.getApiMetaHashKey(urlMethod), new ApiAccessMetaCache(apiAccess))
//        );
    }

    /**
     * 获取正在启动服务的url
     *
     * @param serviceName
     * @return
     */
    private String getFetchUrl(String serviceName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // TODO:
        return null;
//        DiscoveryClient discoveryClient = SpringContextUtil.getBean(DiscoveryClient.class);
//        //DiscoveryClient#refreshRegistry()
//        Method method = DiscoveryClient.class.getDeclaredMethod("refreshRegistry");
//        method.setAccessible(true);
//        method.invoke(discoveryClient);
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
//        GatewayApplication application = discoveryClient.getApplication(serviceName);
//        InstanceInfo instanceInfo = application.getInstances().get(0);
//        return "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + ApiMetaConstants.FETCH_URL;
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
        });
    }

}