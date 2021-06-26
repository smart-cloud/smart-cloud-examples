package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.bo.meta.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessBO;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessContext;
import org.smartframework.cloud.examples.support.gateway.filter.log.LogContext;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.starter.core.business.exception.RepeatSubmitException;
import org.smartframework.cloud.utility.JacksonUtil;
import org.smartframework.cloud.utility.security.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 重复提交校验
 *
 * @author liyulin
 * @date 2020-09-05
 */
@Slf4j
@Configuration
public class RepeatSubmitCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiAccessBO apiAccessBO = ApiAccessContext.getContext();
        ApiAccessMetaCache apiAccessMetaCache = apiAccessBO.getApiAccessMetaCache();
        if (apiAccessMetaCache == null || apiAccessMetaCache.getRepeatSubmitCheckMeta() == null || !apiAccessMetaCache.getRepeatSubmitCheckMeta().isCheck()) {
            return chain.filter(exchange);
        }

        StringBuilder params = new StringBuilder();
        params.append(LogContext.getApiLogBO().getQueryParams());
        params.append(JacksonUtil.toJson(LogContext.getApiLogBO().getArgs()));

        String paramMd5 = Md5Util.md5Hex(params.toString());

        String checkKey = RedisKeyHelper.getRepeatSubmitCheckKey(apiAccessBO.getToken(), paramMd5);
        long expireMillis = apiAccessMetaCache.getRepeatSubmitCheckMeta().getExpireMillis();
        RLock lock = redissonClient.getLock(checkKey);
        boolean isPermitSubmit = false;
        try {
            isPermitSubmit = lock.tryLock();
            if (!isPermitSubmit) {
                throw new RepeatSubmitException(null);
            }
            return chain.filter(exchange);
        } finally {
            if (isPermitSubmit) {
                // 执行完后expireMillis毫秒内不允许重复提交
                if (expireMillis > 0) {
                    lock.lock(expireMillis, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Order.REPEAT_SUBMIT_CHECK;
    }

}