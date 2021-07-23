package org.smartframework.cloud.examples.support.gateway.filter.access;

import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 过滤器base类
 *
 * @author collin
 * @date 2021-07-23
 */
public abstract class AbstractFilter implements WebFilter, Ordered {

    protected abstract Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        FilterContext filterContext = (FilterContext) exchange.getAttributes().get(GatewayConstants.FILTER_CONTEXT_KEY);
        if (filterContext == null
                || filterContext.getApiAccessMetaCache() == null) {
            return chain.filter(exchange);
        }

        return filter(exchange, chain, filterContext);
    }

}