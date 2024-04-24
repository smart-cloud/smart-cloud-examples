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

import io.github.smart.cloud.common.web.constants.SmartHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * smart cloud自定义注解对应入参处理，供后续filter使用
 *
 * @author collin
 * @date 2020-09-08
 */
@Component
@RequiredArgsConstructor
public class ApiAccessFilter implements WebFilter, Ordered {

    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.TOKEN);
        if (StringUtils.isBlank(token)) {
            return chain.filter(exchange);
        }

        String urlMethod = request.getURI().getPath() + request.getMethodValue();
        ApiAccessMetaCache apiAccessMetaCache = (ApiAccessMetaCache) redisTemplate.opsForHash().get(RedisKeyHelper.getApiMetaKey(),
                RedisKeyHelper.getApiMetaHashKey(urlMethod));

        // 将数据塞入当前context，供后面filter使用
        FilterContext filterContext = new FilterContext()
                .setToken(token)
                .setApiAccessMetaCache(apiAccessMetaCache)
                .setUrlMethod(urlMethod);
        exchange.getAttributes().put(GatewayConstants.FILTER_CONTEXT_KEY, filterContext);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Order.API_ACCESS;
    }

}