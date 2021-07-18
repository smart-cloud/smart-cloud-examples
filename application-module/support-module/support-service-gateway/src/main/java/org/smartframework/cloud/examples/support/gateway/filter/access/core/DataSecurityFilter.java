package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.smartframework.cloud.api.core.annotation.enums.SignType;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessBO;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessContext;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 数据安全（加解密、签名）处理过滤器
 *
 * @author collin
 * @date 2021-07-16
 */
@Configuration
public class DataSecurityFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return Order.DATA_SECURITY;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiAccessBO apiAccessBO = ApiAccessContext.getContext();
        ApiAccessMetaCache apiAccessMetaCache = apiAccessBO.getApiAccessMetaCache();
        if (apiAccessMetaCache == null || !apiAccessMetaCache.isRepeatSubmitCheck()) {
            return chain.filter(exchange);
        }

        //接口签名类型
        byte signType = apiAccessMetaCache.getSignType();
        // 请求信息验签
        if (SignType.REQUEST.getType() == signType || SignType.ALL.getType() == signType) {

        }

        //请求参数是否需要解密
        boolean requestDecrypt = apiAccessMetaCache.isRequestDecrypt();
        if (requestDecrypt) {

        }

        //响应信息是否需要加密
        boolean responseEncrypt = apiAccessMetaCache.isResponseEncrypt();
        if (responseEncrypt) {

        }

        // 响应信息签名
        if (SignType.RESPONSE.getType() == signType || SignType.ALL.getType() == signType) {

        }
        return chain.filter(exchange);
    }

}