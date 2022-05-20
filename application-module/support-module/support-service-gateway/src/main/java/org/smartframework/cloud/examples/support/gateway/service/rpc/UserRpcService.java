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

import io.github.smart.cloud.exception.BusinessException;
import io.github.smart.cloud.exception.DataValidateException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.MySmartUser;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.RedisMaxIdle;
import org.smartframework.cloud.examples.support.gateway.constants.RedisTtl;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author collin
 * @date 2020-09-11
 */
@Service
@RequiredArgsConstructor
public class UserRpcService {

    private final RedissonClient redissonClient;

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
        cacheAuth(req.getUid(), req.getRoles(), req.getPermissions());

        // 3、删除用户权限二级缓存
        RMapCache<String, Boolean> userAuthSecondaryCacheMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserAuthSecondaryCacheHashKey(req.getToken()));
        userAuthSecondaryCacheMapCache.clear();

        // 4、删除上一次登录的信息（如果存在） && 保存新的token与userId关系
        deleteOldCacheAndSaveRela(req.getUid(), req.getToken());
    }

    /**
     * 刷新有效期
     *
     * @param token
     */
    public void refreshUserCacheExpiration(String token, Long uid) {
        // 1、刷新security key
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        securityKeyMapCache.updateEntryExpirationAsync(RedisKeyHelper.getSecurityKey(token), RedisTtl.SECURITY_KEY_LOGIN_SUCCESS, TimeUnit.MILLISECONDS, RedisTtl.SECURITY_KEY_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);

        // 2、刷新用户信息
        RMapCache<String, MySmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.updateEntryExpirationAsync(RedisKeyHelper.getUserKey(token), RedisTtl.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS, RedisMaxIdle.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);

        // 3、刷新权限信息
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.updateEntryExpirationAsync(RedisKeyHelper.getAuthKey(uid), RedisTtl.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS, RedisMaxIdle.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);
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
        securityKeyMapCache.put(RedisKeyHelper.getSecurityKey(token), securityKeyCache, RedisTtl.SECURITY_KEY_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);
    }

    private void cacheUser(CacheUserInfoReqDTO req) {
        MySmartUser mySmartUserCache = new MySmartUser();
        Long uid = req.getUid();
        mySmartUserCache.setId(uid);
        mySmartUserCache.setUsername(req.getUsername());
        mySmartUserCache.setRealName(req.getRealName());
        mySmartUserCache.setMobile(req.getMobile());

        RMapCache<String, MySmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.put(RedisKeyHelper.getUserKey(req.getToken()), mySmartUserCache, RedisTtl.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS, RedisMaxIdle.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);
    }

    public void cacheAuth(@NotNull Long uid, Set<String> roles, Set<String> permissions) {
        AuthCache authCache = new AuthCache();
        authCache.setRoles(roles);
        authCache.setPermissions(permissions);

        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.put(RedisKeyHelper.getAuthKey(uid), authCache, RedisTtl.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS, RedisMaxIdle.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);
    }

    private void deleteOldCacheAndSaveRela(Long uid, String token) {
        // 1、删除上一次登录的信息（如果存在）
        RMapCache<Long, String> userTokenCache = redissonClient.getMapCache(RedisKeyHelper.getUserTokenRelationHashKey());
        String oldToken = userTokenCache.get(RedisKeyHelper.getUserTokenRelationKey(uid));
        if (StringUtils.isNotBlank(oldToken)) {
            removeSession(oldToken, uid);
        }

        // 2、保存新的token与userId关系
        userTokenCache.put(RedisKeyHelper.getUserTokenRelationKey(uid), token, RedisTtl.USER_LOGIN_SUCCESS, TimeUnit.MILLISECONDS);
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitLoginReqDTO req) {
        RMapCache<String, MySmartUser> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        MySmartUser mySmartUser = userCache.get(RedisKeyHelper.getUserKey(req.getToken()));
        if (mySmartUser == null) {
            throw new BusinessException(GatewayReturnCodes.TOKEN_EXPIRED_LOGIN_SUCCESS);
        }
        removeSession(req.getToken(), mySmartUser.getId());
    }

    private void removeSession(String token, Long uid) {
        // 1、删除security key
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        securityKeyMapCache.remove(RedisKeyHelper.getSecurityKey(token));

        // 2、删除用户信息
        RMapCache<String, MySmartUser> userMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        userMapCache.remove(RedisKeyHelper.getUserKey(token));

        // 3、删除权限信息
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        authMapCache.remove(RedisKeyHelper.getAuthKey(uid));

        // 4、删除用户权限二级缓存
        RMapCache<String, Boolean> userAuthSecondaryCacheMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserAuthSecondaryCacheHashKey(token));
        userAuthSecondaryCacheMapCache.clear();
    }

}