package org.smartframework.cloud.examples.support.gateway.service.rpc;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.SmartUser;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqVO;
import org.smartframework.cloud.exception.ServerException;
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
        delaySecurityKeyExpire(req.getToken());

        // 2、缓存用户信息
        cacheUser(req);
        cacheAuth(req);

        // 3、删除上一次登录的信息（如果存在） && 保存新的token与userId关系
        deleteOldCacheAndSaveRela(req.getUserId(), req.getToken());
    }

    /**
     * 延长security key有效期
     *
     * @param token
     */
    private void delaySecurityKeyExpire(String token) {
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        SecurityKeyCache securityKeyCache = securityKeyMapCache.get(RedisKeyHelper.getSecurityKey(token));
        if (securityKeyCache == null) {
            throw new ServerException(GatewayReturnCodes.TOKEN_EXPIRED_BEFORE_LOGIN);
        }
        securityKeyMapCache.put(RedisKeyHelper.getSecurityKey(token), securityKeyCache, RedisExpire.SECURITY_KEY_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    private void cacheUser(CacheUserInfoReqVO req) {
        SmartUser smartUserCache = new SmartUser();
        Long uid = req.getUserId();
        smartUserCache.setId(uid);
        smartUserCache.setUsername(req.getUsername());
        smartUserCache.setRealName(req.getRealName());
        smartUserCache.setMobile(req.getMobile());

        RMapCache<String, SmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.put(RedisKeyHelper.getUserKey(req.getToken()), smartUserCache, RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    private void cacheAuth(CacheUserInfoReqVO req) {
        AuthCache authCache = new AuthCache();
        authCache.setRoles(req.getRoles());
        authCache.setPermissions(req.getPermissions());

        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.put(RedisKeyHelper.getAuthKey(req.getToken()), authCache, RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    private void deleteOldCacheAndSaveRela(Long uid, String token) {
        // 1、删除上一次登录的信息（如果存在）
        RMapCache<Long, String> userTokenCache = redissonClient.getMapCache(RedisKeyHelper.getUserTokenRelationHashKey());
        String oldToken = userTokenCache.get(RedisKeyHelper.getUserTokenRelationKey(uid));
        if (StringUtils.isNotBlank(oldToken)) {
            removeSession(oldToken);
        }

        // 2、保存新的token与userId关系
        userTokenCache.put(RedisKeyHelper.getUserTokenRelationKey(uid), token, RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
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
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        securityKeyMapCache.remove(RedisKeyHelper.getSecurityKey(token));

        // 2、删除用户信息
        RMapCache<String, SmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.remove(RedisKeyHelper.getUserKey(token));

        // 3、删除权限信息
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.remove(RedisKeyHelper.getAuthKey(token));
    }

}