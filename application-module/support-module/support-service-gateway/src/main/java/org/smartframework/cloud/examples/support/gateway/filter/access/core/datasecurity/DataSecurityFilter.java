package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.exception.ParamValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 数据安全（加解密、签名）处理过滤器
 *
 * @author collin
 * @date 2021-07-16
 */
@Slf4j
@Component
public class DataSecurityFilter extends AbstractFilter {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public int getOrder() {
        return Order.DATA_SECURITY;
    }

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        if (!apiAccessMetaCache.isDataSecurity()) {
            return chain.filter(exchange);
        }

        String token = filterContext.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ParamValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        HttpMethod httpMethod = exchange.getRequest().getMethod();
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        if (!match(contentType, httpMethod)) {
            return chain.filter(exchange);
        }

        return chain.filter(exchange.mutate()
                .request(new DataSecurityServerHttpRequestDecorator(exchange.getRequest(), token, apiAccessMetaCache.isRequestDecrypt(),
                        apiAccessMetaCache.getSignType(), redissonClient))
                .response(new DataSecurityServerHttpResponseDecorator(exchange.getResponse(), apiAccessMetaCache.isResponseEncrypt(), apiAccessMetaCache.getSignType()))
                .build());
    }

    /**
     * 加解密、签名匹配条件
     *
     * @param contentType
     * @param httpMethod
     * @return
     */
    private boolean match(MediaType contentType, HttpMethod httpMethod) {
        // GET、POST以外的请求不做加解密、签名处理
        return (contentType != null
                && ((httpMethod == HttpMethod.POST && (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())
                || MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())))
                || httpMethod == HttpMethod.GET));
    }

}