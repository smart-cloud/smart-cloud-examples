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
import io.github.smart.cloud.starter.redis.adapter.IRedisAdapter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.app.auth.core.MySmartUser;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.RedisTtl;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户
 *
 * @author collin
 * @date 2020-09-11
 */
@Service
@RequiredArgsConstructor
public class UserRpcService {

    private final IRedisAdapter redisAdapter;

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
        redisAdapter.delete(RedisKeyHelper.getUserAuthSecondaryCacheKey(req.getToken()));

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
        redisAdapter.expire(RedisKeyHelper.getSecurityKey(token), RedisTtl.SECURITY_KEY_LOGIN_SUCCESS);

        // 2、刷新用户信息
        redisAdapter.expire(RedisKeyHelper.getUserKey(token), RedisTtl.USER_LOGIN_SUCCESS);

        // 3、刷新权限信息
        redisAdapter.expire(RedisKeyHelper.getAuthKey(uid), RedisTtl.USER_LOGIN_SUCCESS);
    }

    /**
     * 延长security key有效期
     *
     * @param token
     */
    private void delaySecurityKeyExpire(String token) {
        String cacheKey = RedisKeyHelper.getSecurityKey(token);
        SecurityKeyCache securityKeyCache = (SecurityKeyCache)redisAdapter.get(cacheKey);
        if (securityKeyCache == null) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_EXPIRED_BEFORE_LOGIN);
        }

        redisAdapter.expire(cacheKey, RedisTtl.SECURITY_KEY_LOGIN_SUCCESS);
    }

    private void cacheUser(CacheUserInfoReqDTO req) {
        MySmartUser mySmartUserCache = new MySmartUser();
        Long uid = req.getUid();
        mySmartUserCache.setId(uid);
        mySmartUserCache.setUsername(req.getUsername());
        mySmartUserCache.setRealName(req.getRealName());
        mySmartUserCache.setMobile(req.getMobile());

        redisAdapter.set(RedisKeyHelper.getUserKey(req.getToken()), mySmartUserCache, RedisTtl.USER_LOGIN_SUCCESS);
    }

    public void cacheAuth(@NotNull Long uid, Set<String> roles, Set<String> permissions) {
        AuthCache authCache = new AuthCache();
        authCache.setRoles(roles);
        authCache.setPermissions(permissions);

        redisAdapter.set(RedisKeyHelper.getAuthKey(uid), authCache, RedisTtl.USER_LOGIN_SUCCESS);
    }

    private void deleteOldCacheAndSaveRela(Long uid, String token) {
        // 1、删除上一次登录的信息（如果存在）
        String userTokenRelationKey = RedisKeyHelper.getUserTokenRelationKey(uid);
        String oldToken = (String)redisAdapter.get(userTokenRelationKey);
        if (StringUtils.isNotBlank(oldToken)) {
            removeSession(oldToken, uid);
        }

        // 2、保存新的token与userId关系
        redisAdapter.set(userTokenRelationKey, token, RedisTtl.USER_LOGIN_SUCCESS);
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitLoginReqDTO req) {
        MySmartUser mySmartUser = (MySmartUser)redisAdapter.get(RedisKeyHelper.getUserKey(req.getToken()));
        if (mySmartUser == null) {
            throw new BusinessException(GatewayReturnCodes.TOKEN_EXPIRED_LOGIN_SUCCESS);
        }

        removeSession(req.getToken(), mySmartUser.getId());
    }

    private void removeSession(String token, Long uid) {
        // 1、删除security key
        redisAdapter.delete(RedisKeyHelper.getSecurityKey(token));

        // 2、删除用户信息
        redisAdapter.delete(RedisKeyHelper.getUserKey(token));

        // 3、删除权限信息
        redisAdapter.delete(RedisKeyHelper.getAuthKey(uid));

        // 4、删除用户权限二级缓存
        redisAdapter.delete(RedisKeyHelper.getUserAuthSecondaryCacheKey(token));
    }

}