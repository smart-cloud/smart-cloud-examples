package org.smartframework.cloud.examples.support.gateway.filter.rewrite;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * http请求重写过滤器
 *
 * @author collin
 * @date 2021-07-23
 */
@Component
@Slf4j
public class RewriteHttpFilter implements WebFilter, Ordered {

    @Override
    public int getOrder() {
        return Order.REWRITE_HTTP;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange.mutate()
                .request(new RewriteServerHttpRequestDecorator(exchange.getRequest()))
                .response(new RewriteServerHttpResponseDecorator(exchange.getResponse()))
                .build());
    }

}