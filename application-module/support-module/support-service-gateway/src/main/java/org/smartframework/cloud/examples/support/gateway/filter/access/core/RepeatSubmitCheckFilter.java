package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessBO;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessContext;
import org.smartframework.cloud.examples.support.gateway.filter.log.LogContext;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.starter.core.business.exception.RepeatSubmitException;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.smartframework.cloud.utility.JacksonUtil;
import org.smartframework.cloud.utility.security.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
    private RedisComponent redisComponent;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiAccessBO apiAccessBO = ApiAccessContext.getContext();
        ApiMetaFetchRespVO.ApiAC apiAC = apiAccessBO.getApiAC();
        if (apiAC == null || !apiAC.isRepeatSubmitCheck()) {
            return chain.filter(exchange);
        }

        StringBuilder params = new StringBuilder();
        params.append(LogContext.getApiLogBO().getQueryParams());
        params.append(JacksonUtil.toJson(LogContext.getApiLogBO().getArgs()));

        String paramMd5 = Md5Util.md5Hex(params.toString());

        String checkKey = RedisKeyHelper.getRepeatSubmitCheckKey(apiAccessBO.getToken());
        if (!redisComponent.setNx(checkKey, paramMd5, 20000)) {
            log.error("repeat.submit.reject==>{}", LogContext.getApiLogBO());
            throw new RepeatSubmitException(null);
        }

        return chain.filter(exchange).doFinally(s -> {
            redisComponent.delete(checkKey);
        });
    }

    @Override
    public int getOrder() {
        return Order.REPEAT_SUBMIT_CHECK;
    }

}