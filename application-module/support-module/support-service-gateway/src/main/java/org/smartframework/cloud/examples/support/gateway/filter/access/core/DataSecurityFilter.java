package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.smartframework.cloud.api.core.annotation.enums.SignType;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpResponseDecorator;
import org.springframework.http.HttpMethod;
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

    @Override
    public int getOrder() {
        return Order.DATA_SECURITY;
    }

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        if (apiAccessMetaCache == null || !apiAccessMetaCache.isDataSecurity()) {
            return chain.filter(exchange);
        }
        HttpMethod httpMethod = exchange.getRequest().getMethod();
        // GET、POST以外的请求不做加解密、签名处理
        if (httpMethod != HttpMethod.POST && httpMethod != HttpMethod.GET) {
            return chain.filter(exchange);
        }

        // TODO:
        // request：GET、POST（application/x-www-form-urlencoded、application/json）
        // response：application/json

        //接口签名类型
        byte signType = apiAccessMetaCache.getSignType();
        // 请求信息验签
        if (SignType.REQUEST.getType() == signType || SignType.ALL.getType() == signType) {
            RewriteServerHttpRequestDecorator rewriteServerHttpRequestDecorator = (RewriteServerHttpRequestDecorator) exchange.getRequest();
            String requestBodyStr = rewriteServerHttpRequestDecorator.getBodyStr();
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

}