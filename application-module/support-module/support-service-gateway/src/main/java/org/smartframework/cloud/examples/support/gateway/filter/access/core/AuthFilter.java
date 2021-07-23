package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.AppAuthConstants;
import org.smartframework.cloud.examples.app.auth.core.SmartUser;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.cache.AuthCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.AuthenticationException;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.exception.BusinessException;
import org.smartframework.cloud.exception.DataValidateException;
import org.smartframework.cloud.utility.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
 * @author liyulin
 * @date 2020-09-11
 */
@Component
public class AuthFilter extends AbstractFilter {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public int getOrder() {
        return Order.AUTH_CHECK;
    }

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        String token = filterContext.getToken();

        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        // 判断是否需要登陆、鉴权
        if (apiAccessMetaCache == null || !apiAccessMetaCache.isAuth()) {
            return chain.filter(exchange);
        }

        if (StringUtils.isBlank(token)) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        RMapCache<String, SmartUser> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        SmartUser smartUser = userCache.get(RedisKeyHelper.getUserKey(token));
        if (smartUser == null) {
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
        boolean pass = checkAuth(apiAccessMetaCache, token);
        userAuthSecondaryCacheMapCache.put(filterContext.getUrlMethod(), pass, RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
        if (!pass) {
            throw new AuthenticationException();
        }

        // 3、将用户信息塞入http header
        ServerHttpRequest newServerHttpRequest = fillUserInHeader(exchange.getRequest(), smartUser);
        return chain.filter(exchange.mutate().request(newServerHttpRequest).build());
    }

    /**
     * 鉴权
     *
     * @param apiAccessMetaCache
     * @param token
     * @return
     */
    private boolean checkAuth(ApiAccessMetaCache apiAccessMetaCache, String token) {
        boolean requirePermission = CollectionUtils.isNotEmpty(apiAccessMetaCache.getRequiresPermissions());
        boolean requireRole = CollectionUtils.isNotEmpty(apiAccessMetaCache.getRequiresRoles());
        if (!requirePermission && !requireRole) {
            return true;
        }
        RMapCache<String, AuthCache> authMapCache = redissonClient.getMapCache(RedisKeyHelper.getAuthHashKey());
        AuthCache authCache = authMapCache.get(RedisKeyHelper.getAuthKey(token));
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
     * 将用户登录相关信息塞进http header中，供后续服务使用（后续服务将从header中取）
     *
     * @param request
     * @param smartUser
     */
    public ServerHttpRequest fillUserInHeader(ServerHttpRequest request, SmartUser smartUser) {
        // base64处理，解决中文乱码
        String authUserBase64 = Base64Utils.encodeToUrlSafeString(JacksonUtil.toJson(smartUser).getBytes(StandardCharsets.UTF_8));
        return request.mutate().header(AppAuthConstants.HEADER_USER, authUserBase64).build();
    }

}