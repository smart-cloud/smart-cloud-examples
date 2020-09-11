package org.smartframework.cloud.examples.support.gateway.service.rpc;

import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO.ApiAC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liyulin
 * @desc 接口元数据（签名、加解密、权限等）处理
 * @date 2020/04/28
 */
@Service
public class ApiMetaRpcService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存接口元数据
     *
     * @param req
     * @return
     */
    public void save(ApiMetaUploadReqVO req) {
        Map<String, ApiAC> apiACs = req.getApiACs();

        // redis持久化
        apiACs.forEach((urlMethod, apiAC) ->
                redisTemplate.opsForHash().put(RedisKeyHelper.getApiMetaKey(), RedisKeyHelper.getApiMetaHashKey(urlMethod), apiAC)
        );
    }

}