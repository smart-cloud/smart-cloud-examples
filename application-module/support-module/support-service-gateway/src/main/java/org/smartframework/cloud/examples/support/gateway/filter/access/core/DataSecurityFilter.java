package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.api.core.annotation.enums.SignType;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpResponseDecorator;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
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

        ServerHttpRequest request = exchange.getRequest();
        HttpMethod httpMethod = exchange.getRequest().getMethod();
        // GET、POST以外的请求不做加解密、签名处理
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        if (!match(contentType, httpMethod)) {
            return chain.filter(exchange);
        }

        // TODO:
        // request：GET、POST（application/x-www-form-urlencoded、application/json）
        // response：application/json

        //接口签名类型
        byte signType = apiAccessMetaCache.getSignType();
        // 请求信息验签
        if (SignType.REQUEST.getType() == signType || SignType.ALL.getType() == signType) {
            String requestStr = null;
            if (httpMethod == HttpMethod.GET) {
                requestStr = request.getQueryParams().getFirst(GatewayConstants.REQUEST_ENCRYPT_PARAM_NAME);
            } else if (httpMethod == HttpMethod.POST) {
                if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())) {
                    requestStr = request.getQueryParams().getFirst(GatewayConstants.REQUEST_ENCRYPT_PARAM_NAME);
                } else if (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())) {
                    RewriteServerHttpRequestDecorator rewriteServerHttpRequestDecorator = (RewriteServerHttpRequestDecorator) exchange.getRequest();
                    requestStr = rewriteServerHttpRequestDecorator.getBodyStr();
                }
            }
            if (StringUtils.isNotBlank(requestStr)) {
                RMapCache<String, SecurityKeyCache> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
                SecurityKeyCache securityKeyCache = authCache.get(RedisKeyHelper.getSecurityKey(token));
            }
        }

        //请求参数是否需要解密
        boolean requestDecrypt = apiAccessMetaCache.isRequestDecrypt();
        if (requestDecrypt) {

        }

        return chain.filter(exchange).doFinally(s -> {
            //响应信息是否需要加密
            boolean responseEncrypt = apiAccessMetaCache.isResponseEncrypt();
            if (responseEncrypt) {
                RewriteServerHttpResponseDecorator rewriteServerHttpResponseDecorator = (RewriteServerHttpResponseDecorator) exchange.getResponse();
                String responseBodyStr = rewriteServerHttpResponseDecorator.getBodyStr();
            }

            // 响应信息签名
            if (SignType.RESPONSE.getType() == signType || SignType.ALL.getType() == signType) {

            }
        });
    }

    /**
     * 加解密、签名匹配条件
     *
     * @param contentType
     * @param httpMethod
     * @return
     */
    private boolean match(MediaType contentType, HttpMethod httpMethod) {
        return (contentType != null
                && ((httpMethod == HttpMethod.POST && (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())
                || MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())))
                || httpMethod == HttpMethod.GET));
    }

}