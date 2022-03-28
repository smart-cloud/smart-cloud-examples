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

    /**
     * 内部过滤器
     *
     * @param exchange
     * @param chain
     * @param filterContext
     * @return
     */
    protected abstract Mono<Void> innerFilter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        FilterContext filterContext = (FilterContext) exchange.getAttributes().get(GatewayConstants.FILTER_CONTEXT_KEY);
        if (filterContext == null || filterContext.getApiAccessMetaCache() == null) {
            return chain.filter(exchange);
        }

        return innerFilter(exchange, chain, filterContext);
    }

}