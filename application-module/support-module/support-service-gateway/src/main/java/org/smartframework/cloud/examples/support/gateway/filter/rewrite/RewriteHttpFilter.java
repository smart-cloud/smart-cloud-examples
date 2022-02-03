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
package org.smartframework.cloud.examples.support.gateway.filter.rewrite;

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
public class RewriteHttpFilter implements WebFilter, Ordered {

    @Override
    public int getOrder() {
        return Order.REWRITE_HTTP;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange.mutate()
                .request(new RewriteServerHttpRequestDecorator(exchange.getRequest(), exchange.getResponse().bufferFactory()))
                .response(new RewriteServerHttpResponseDecorator(exchange.getResponse()))
                .build());
    }

}