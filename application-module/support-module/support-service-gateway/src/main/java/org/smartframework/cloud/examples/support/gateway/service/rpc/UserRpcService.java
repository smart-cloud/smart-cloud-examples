package org.smartframework.cloud.examples.support.gateway.service.rpc;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.MySmartUser;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.smartframework.cloud.exception.DataValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
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
    public void cacheUserInfo(CacheUserInfoReqDTO req) {
        // 1、更新security key有效期
        delaySecurityKeyExpire(req.getToken());

        // 2、缓存用户信息
        cacheUser(req);
        cacheAuth(req.getToken(), req.getRoles(), req.getPermissions());

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
            throw new DataValidateException(GatewayReturnCodes.TOKEN_EXPIRED_BEFORE_LOGIN);
        }
        securityKeyMapCache.put(RedisKeyHelper.getSecurityKey(token), securityKeyCache, RedisExpire.SECURITY_KEY_EXPIRE_SECONDS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    private void cacheUser(CacheUserInfoReqDTO req) {
        MySmartUser mySmartUserCache = new MySmartUser();
        Long uid = req.getUserId();
        mySmartUserCache.setId(uid);
        mySmartUserCache.setUsername(req.getUsername());
        mySmartUserCache.setRealName(req.getRealName());
        mySmartUserCache.setMobile(req.getMobile());

        RMapCache<String, MySmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.put(RedisKeyHelper.getUserKey(req.getToken()), mySmartUserCache, RedisExpire.USER_EXPIRE_SECONDS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    public void cacheAuth(@NonNull String token, Set<String> roles, Set<String> permissions) {
        AuthCache authCache = new AuthCache();
        authCache.setRoles(roles);
        authCache.setPermissions(permissions);

        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.put(RedisKeyHelper.getAuthKey(token), authCache, RedisExpire.USER_EXPIRE_SECONDS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    private void deleteOldCacheAndSaveRela(Long uid, String token) {
        // 1、删除上一次登录的信息（如果存在）
        RMapCache<Long, String> userTokenCache = redissonClient.getMapCache(RedisKeyHelper.getUserTokenRelationHashKey());
        String oldToken = userTokenCache.get(RedisKeyHelper.getUserTokenRelationKey(uid));
        if (StringUtils.isNotBlank(oldToken)) {
            removeSession(oldToken);
        }

        // 2、保存新的token与userId关系
        userTokenCache.put(RedisKeyHelper.getUserTokenRelationKey(uid), token, RedisExpire.USER_EXPIRE_SECONDS_LOGIN_SUCCESS, TimeUnit.SECONDS);
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitLoginReqDTO req) {
        removeSession(req.getToken());
    }

    private void removeSession(String token) {
        // 1、删除security key
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        securityKeyMapCache.remove(RedisKeyHelper.getSecurityKey(token));

        // 2、删除用户信息
        RMapCache<String, MySmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.remove(RedisKeyHelper.getUserKey(token));

        // 3、删除权限信息
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.remove(RedisKeyHelper.getAuthKey(token));

        // 4、删除用户权限二级缓存
        RMapCache<String, Boolean> userAuthSecondaryCacheMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserAuthSecondaryCacheHashKey(token));
        userAuthSecondaryCacheMapCache.clear();
    }

}