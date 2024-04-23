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
package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import io.github.smart.cloud.exception.DataValidateException;
import io.github.smart.cloud.exception.ParamValidateException;
import io.github.smart.cloud.starter.redis.adapter.IRedisAdapter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.springframework.lang.NonNull;
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
@RequiredArgsConstructor
public class DataSecurityFilter extends AbstractFilter {

    private final IRedisAdapter redisAdapter;

    @Override
    public int getOrder() {
        return Order.DATA_SECURITY;
    }

    @Override
    protected Mono<Void> innerFilter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        if (!apiAccessMetaCache.isDataSecurity()) {
            return chain.filter(exchange);
        }

        String token = filterContext.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ParamValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        SecurityKeyCache securityKeyCache = getSecurityKeyCache(token);
        return chain.filter(exchange.mutate()
                .request(new DataSecurityServerHttpRequestDecorator(exchange.getRequest(), exchange.getResponse().bufferFactory(), securityKeyCache,
                        apiAccessMetaCache.isRequestDecrypt(), apiAccessMetaCache.getSignType()))
                .response(new DataSecurityServerHttpResponseDecorator(exchange.getResponse(), securityKeyCache, apiAccessMetaCache.isResponseEncrypt(),
                        apiAccessMetaCache.getSignType()))
                .build());
    }

    private SecurityKeyCache getSecurityKeyCache(@NonNull String token) {
        SecurityKeyCache securityKeyCache = (SecurityKeyCache) redisAdapter.get(RedisKeyHelper.getSecurityKey(token));
        if (securityKeyCache == null) {
            throw new DataValidateException(GatewayReturnCodes.SECURITY_KEY_EXPIRED);
        }

        return securityKeyCache;
    }

}