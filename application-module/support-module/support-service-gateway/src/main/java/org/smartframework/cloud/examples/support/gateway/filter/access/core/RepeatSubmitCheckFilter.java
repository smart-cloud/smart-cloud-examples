package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.exception.RepeatSubmitException;
import org.smartframework.cloud.utility.security.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 重复提交校验
 *
 * @author liyulin
 * @date 2020-09-05
 */
@Component
public class RepeatSubmitCheckFilter extends AbstractFilter {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        ApiAccessMetaCache apiAccessMetaCache = filterContext.getApiAccessMetaCache();
        if (apiAccessMetaCache == null || !apiAccessMetaCache.isRepeatSubmitCheck()) {
            return chain.filter(exchange);
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