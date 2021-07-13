package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.app.auth.core.AppAuthConstants;
import org.smartframework.cloud.examples.app.auth.core.UserBO;
import org.smartframework.cloud.examples.support.gateway.bo.meta.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessBO;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessContext;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.exception.BusinessException;
import org.smartframework.cloud.exception.DataValidateException;
import org.smartframework.cloud.utility.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 用户token信息处理
 * <p>1、校验token</p>
 * <p>2、将用户信息从缓存中塞进http header中，供后序服务使用</p>
 *
 * @author liyulin
 * @date 2020-09-11
 */
@Slf4j
@Configuration
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public int getOrder() {
        return Order.AUTH_CHECK;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiAccessBO apiAccessBO = ApiAccessContext.getContext();
        String token = apiAccessBO.getToken();
        ApiAccessMetaCache apiAccessMetaCache = apiAccessBO.getApiAccessMetaCache();
        // 判断是否需要登陆、鉴权
        if (apiAccessMetaCache == null || !apiAccessMetaCache.isAuth()) {
            return chain.filter(exchange);
        }

        if (StringUtils.isBlank(token)) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        RMapCache<String, UserBO> userCache = redissonClient.getMapCache(RedisKeyHelper.getUserHashKey());
        UserBO userBO = userCache.get(RedisKeyHelper.getUserKey(token));
        if (userBO == null) {
            throw new BusinessException(GatewayReturnCodes.TOKEN_EXPIRED_LOGIN_SUCCESS);
        }

        // 鉴权
        if (apiAccessMetaCache.getRequiresPermissions().size() > 0) {

        }
        if (apiAccessMetaCache.getRequiresRoles().size() > 0) {

        }

        // 2、将用户信息塞入http header
        ServerHttpRequest newServerHttpRequest = fillUserInHeader(exchange.getRequest(), userBO);
        return chain.filter(exchange.mutate().request(newServerHttpRequest).build());
    }

    /**
     * 将用户登录相关信息塞进http header中，供后续服务使用（后续服务将从header中取）
     *
     * @param request
     * @param userBO
     */
    public ServerHttpRequest fillUserInHeader(ServerHttpRequest request, UserBO userBO) {
        // base64处理，解决中文乱码
        String authUserBase64 = Base64Utils.encodeToUrlSafeString(JacksonUtil.toJson(userBO).getBytes(StandardCharsets.UTF_8));
        return request.mutate().header(AppAuthConstants.HEADER_USER, authUserBase64).build();
    }

}