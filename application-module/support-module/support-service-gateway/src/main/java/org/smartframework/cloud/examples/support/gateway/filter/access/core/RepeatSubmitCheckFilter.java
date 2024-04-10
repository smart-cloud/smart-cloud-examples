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
package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import io.github.smart.cloud.exception.DataValidateException;
import io.github.smart.cloud.exception.RepeatSubmitException;
import io.github.smart.cloud.utility.security.Md5Util;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 重复提交校验
 *
 * @author collin
 * @date 2020-09-05
 */
@Component
@RequiredArgsConstructor
public class RepeatSubmitCheckFilter extends AbstractFilter {

    private final RedissonClient redissonClient;

    @Override
    protected Mono<Void> innerFilter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        if (!apiAccessMetaCache.isRepeatSubmitCheck()) {
            return chain.filter(exchange);
        }

        if (StringUtils.isBlank(filterContext.getToken())) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_MISSING);
        }

        ServerHttpRequest request = exchange.getRequest();

        StringBuilder key = new StringBuilder();
        key.append(filterContext.getUrlMethod());
        key.append(request.getURI().getQuery());
        if (request instanceof RewriteServerHttpRequestDecorator) {
            RewriteServerHttpRequestDecorator rewriteServerHttpRequest = (RewriteServerHttpRequestDecorator) request;
            key.append(rewriteServerHttpRequest.getBodyStr());
        }

        String keyMd5 = Md5Util.md5Hex(key.toString());

        String checkKey = RedisKeyHelper.getRepeatSubmitCheckKey(filterContext.getToken(), keyMd5);
        long expireMillis = apiAccessMetaCache.getRepeatSubmitExpireMillis();
        RLock lock = redissonClient.getLock(checkKey);
        boolean isPermitSubmit = false;
        boolean executeSuccess = true;
        try {
            isPermitSubmit = lock.tryLock();
            if (!isPermitSubmit) {
                throw new RepeatSubmitException(null);
            }
            return chain.filter(exchange);
        } catch (Exception e) {
            executeSuccess = false;
            throw e;
        } finally {
            if (isPermitSubmit) {
                // 成功执行完后expireMillis毫秒内不允许重复提交
                if (executeSuccess && expireMillis > 0) {
                    lock.lock(expireMillis, TimeUnit.MILLISECONDS);
                } else {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Order.REPEAT_SUBMIT_CHECK;
    }

}