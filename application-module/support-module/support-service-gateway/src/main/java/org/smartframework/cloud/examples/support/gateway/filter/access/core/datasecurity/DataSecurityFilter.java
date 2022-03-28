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

import io.github.smart.cloud.exception.ParamValidateException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

    private final RedissonClient redissonClient;

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

        HttpMethod httpMethod = exchange.getRequest().getMethod();
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        if (!match(contentType, httpMethod)) {
            return chain.filter(exchange);
        }

        return chain.filter(exchange.mutate()
                .request(new DataSecurityServerHttpRequestDecorator(exchange.getRequest(), exchange.getResponse().bufferFactory(), token,
                        apiAccessMetaCache.isRequestDecrypt(), apiAccessMetaCache.getSignType(), redissonClient))
                .response(new DataSecurityServerHttpResponseDecorator(exchange.getResponse(), apiAccessMetaCache.isResponseEncrypt(),
                        apiAccessMetaCache.getSignType()))
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