package org.smartframework.cloud.examples.support.gateway.service.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.api.ac.core.constants.ApiMetaConstants;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodeEnum;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqVO;
import org.smartframework.cloud.starter.core.business.exception.BusinessException;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.utility.HttpUtil;
import org.smartframework.cloud.utility.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author liyulin
 * @desc 接口元数据（签名、加解密、权限等）处理
 * @date 2020/04/28
 */
@Service
@Slf4j
public class ApiMetaRpcService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void notifyFetch(NotifyFetchReqVO req) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DiscoveryClient discoveryClient = SpringContextUtil.getBean(DiscoveryClient.class);
        //DiscoveryClient#refreshRegistry()
        Method method = DiscoveryClient.class.getDeclaredMethod("refreshRegistry");
        method.setAccessible(true);
        method.invoke(discoveryClient);

        String url = getFetchUrl(discoveryClient, req.getServiceName());
        RespVO<ApiMetaFetchRespVO> apiMetaFetchRespVO = HttpUtil.get(url, null, new TypeReference<RespVO<ApiMetaFetchRespVO>>() {
        });

        if (!RespUtil.isSuccess(apiMetaFetchRespVO)) {
            throw new BusinessException(GatewayReturnCodeEnum.FETCH_APIMETA_FAIL);
        }
        ApiMetaFetchRespVO apiMetaFetch = apiMetaFetchRespVO.getBody();
        if (apiMetaFetch == null || MapUtils.isEmpty(apiMetaFetch.getApiACs())) {
            return;
        }
        // redis持久化
        apiMetaFetch.getApiACs().forEach((urlMethod, apiAC) ->
                redisTemplate.opsForHash().put(RedisKeyHelper.getApiMetaKey(), RedisKeyHelper.getApiMetaHashKey(urlMethod), apiAC)
        );
    }

    /**
     * 获取正在启动服务的url
     *
     * @param discoveryClient
     * @param serviceName
     * @return
     */
    private String getFetchUrl(DiscoveryClient discoveryClient, String serviceName) {
        Application application = discoveryClient.getApplication(serviceName);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        return "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + ApiMetaConstants.FETCH_URL;
    }

}