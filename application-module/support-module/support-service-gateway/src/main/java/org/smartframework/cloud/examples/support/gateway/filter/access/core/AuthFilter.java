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
package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.common.pojo.ResponseHead;
import io.github.smart.cloud.exception.BusinessException;
import io.github.smart.cloud.exception.DataValidateException;
import io.github.smart.cloud.exception.RpcException;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import io.github.smart.cloud.utility.JacksonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.AppAuthConstants;
import org.smartframework.cloud.examples.app.auth.core.MySmartUser;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.exception.AuthenticationException;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.service.rpc.UserRpcService;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用户token信息处理
 * <p>1、校验token</p>
 * <p>2、鉴权</p>
 * <p>3、将用户信息从缓存中塞进http header中，供后序服务使用</p>
 *
 * @author collin
 * @date 2020-09-11
 */
@Component
@RequiredArgsConstructor
public class AuthFilter extends AbstractFilter {

    private final RedissonClient redissonClient;
    private final ObjectProvider<AuthRpc> authRpcObjectProvider;
    private final UserRpcService userRpcService;

    @Override
    public int getOrder() {
        return Order.AUTH_CHECK;
    }

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        // 判断是否需要登陆、鉴权
        if (!apiAccessMetaCache.isAuth()) {
            return chain.filter(exchange);
        }

        String token = filterContext.getToken();
        if (StringUtils.isBlank(token)) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        RMapCache<String, MySmartUser> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        MySmartUser mySmartUser = userCache.get(RedisKeyHelper.getUserKey(token));
        if (mySmartUser == null) {
            throw new BusinessException(GatewayReturnCodes.TOKEN_EXPIRED_LOGIN_SUCCESS);
        }

        // 1、先从二级缓存判断
        RMapCache<String, Boolean> userAuthSecondaryCacheMapCache = redissonClient.getMapCache(RedisKeyHelper.getUserAuthSecondaryCacheHashKey(token));
        if (userAuthSecondaryCacheMapCache != null) {
            Boolean userAuthSecondaryCachePass = userAuthSecondaryCacheMapCache.get(filterContext.getUrlMethod());
            if (userAuthSecondaryCachePass != null) {
                if (userAuthSecondaryCachePass) {
                    return chain.filter(exchange);
                }
                throw new AuthenticationException();
            }
        }
        // 2、再从一级缓存判断
        boolean pass = checkAuth(apiAccessMetaCache, token, mySmartUser.getId());
        userAuthSecondaryCacheMapCache.put(filterContext.getUrlMethod(), pass, RedisExpire.USER_EXPIRE_SECONDS_LOGIN_SUCCESS, TimeUnit.SECONDS);
        if (!pass) {
            throw new AuthenticationException();
        }

        // 3、将用户信息塞入http header
        ServerHttpRequest newServerHttpRequest = fillUserInHeader(exchange.getRequest(), mySmartUser);
        return chain.filter(exchange.mutate().request(newServerHttpRequest).build());
    }

    /**
     * 鉴权
     *
     * @param apiAccessMetaCache
     * @param token
     * @param uid
     * @return
     */
    private boolean checkAuth(ApiAccessMetaCache apiAccessMetaCache, @NonNull String token, @NonNull Long uid) {
        boolean requirePermission = CollectionUtils.isNotEmpty(apiAccessMetaCache.getRequiresPermissions());
        boolean requireRole = CollectionUtils.isNotEmpty(apiAccessMetaCache.getRequiresRoles());
        if (!requirePermission && !requireRole) {
            return true;
        }

        AuthCache authCache = getAuthCache(token, uid);
        if (authCache == null || (requirePermission && CollectionUtils.isEmpty(authCache.getPermissions()))
                || (requireRole && CollectionUtils.isEmpty(authCache.getRoles()))) {
            return false;
        }

        if (requirePermission && CollectionUtils.isNotEmpty(authCache.getPermissions())) {
            Set<String> requiresPermissions = apiAccessMetaCache.getRequiresPermissions();
            for (String requiresPermission : requiresPermissions) {
                if (authCache.getPermissions().contains(requiresPermission)) {
                    return true;
                }
            }
        }

        if (requireRole && CollectionUtils.isNotEmpty(authCache.getRoles())) {
            Set<String> requiresRoles = apiAccessMetaCache.getRequiresRoles();
            for (String requiresRole : requiresRoles) {
                if (authCache.getRoles().contains(requiresRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取用户权限信息
     *
     * @param token
     * @param uid
     * @return
     */
    private AuthCache getAuthCache(@NonNull String token, @NonNull Long uid) {
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        AuthCache authCache = authMapCache.get(RedisKeyHelper.getAuthKey(token));
        if (authCache != null) {
            return authCache;
        }

        // 如果缓存中没有，则rpc查数据库
        Response<AuthRespDTO> authResponse = authRpcObjectProvider.getIfAvailable().listByUid(uid);
        if (!RespUtil.isSuccess(authResponse)) {
            if (authResponse == null || authResponse.getHead() == null) {
                throw new RpcException();
            }
            ResponseHead head = authResponse.getHead();
            throw new RpcException(head.getCode(), head.getMessage());
        }
        AuthRespDTO authRespDTO = authResponse.getBody();
        if (authRespDTO == null) {
            authCache = new AuthCache();
        } else {
            authCache.setRoles(authRespDTO.getRoles());
            authCache.setPermissions(authRespDTO.getPermissions());
        }
        userRpcService.cacheAuth(token, authCache.getRoles(), authCache.getPermissions());
        return authCache;
    }

    /**
     * 将用户登录相关信息塞进http header中，供后续服务使用（后续服务将从header中取）
     *
     * @param request
     * @param mySmartUser
     */
    public ServerHttpRequest fillUserInHeader(ServerHttpRequest request, MySmartUser mySmartUser) {
        // base64处理，解决中文乱码
        String authUserBase64 = Base64Utils.encodeToUrlSafeString(JacksonUtil.toJson(mySmartUser).getBytes(StandardCharsets.UTF_8));
        return request.mutate().header(AppAuthConstants.HEADER_USER, authUserBase64).build();
    }

}