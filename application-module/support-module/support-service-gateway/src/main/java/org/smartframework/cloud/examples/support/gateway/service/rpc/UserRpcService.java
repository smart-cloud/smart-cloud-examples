package org.smartframework.cloud.examples.support.gateway.service.rpc;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.UserBO;
import org.smartframework.cloud.examples.support.gateway.bo.SecurityKeyBO;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodeEnum;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqVO;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author liyulin
 * @date 2020-09-11
 */
@Service
public class UserRpcService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 登录（或注册）成功后缓存用户信息
     *
     * @param req
     * @return
     */
    public void cacheUserInfo(CacheUserInfoReqVO req) {
        // 1、更新security key有效期
        RMapCache<String, SecurityKeyBO> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        SecurityKeyBO securityKeyBO = authCache.get(RedisKeyHelper.getSecurityKey(req.getToken()));
        if (securityKeyBO == null) {
            throw new ServerException(GatewayReturnCodeEnum.TOKEN_EXPIRED_BEFORE_LOGIN);
        }
        authCache.put(RedisKeyHelper.getSecurityKey(req.getToken()), securityKeyBO, RedisExpire.SECURITY_KEY_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);

        // 2、缓存用户信息
        UserBO userBO = UserBO.builder()
                .id(req.getUserId())
                .username(req.getUsername())
                .realName(req.getRealName())
                .mobile(req.getMobile())
                .build();

        RMapCache<String, UserBO> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userCache.put(RedisKeyHelper.getUserKey(req.getToken()), userBO, RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);

        // 3、删除上一次登录的信息（如果存在）
        RMapCache<Long, String> userTokenCache = redissonClient.getMapCache(RedisKeyHelper.getUserTokenRelationHashKey());
        String oldToken = userTokenCache.get(RedisKeyHelper.getUserTokenRelationKey(req.getUserId()));
        if (StringUtils.isNotBlank(oldToken)) {
            removeSession(oldToken);
        }

        // 4、保存新的token与userId关系
        userTokenCache.put(RedisKeyHelper.getUserTokenRelationKey(req.getUserId()), req.getToken(), RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitLoginReqVO req) {
        removeSession(req.getToken());
    }

    private void removeSession(String token) {
        // 1、删除security key
        RMapCache<String, SecurityKeyBO> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        authCache.remove(RedisKeyHelper.getSecurityKey(token));

        // 2、删除用户信息
        RMapCache<String, UserBO> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userCache.remove(RedisKeyHelper.getUserKey(token));
    }

}