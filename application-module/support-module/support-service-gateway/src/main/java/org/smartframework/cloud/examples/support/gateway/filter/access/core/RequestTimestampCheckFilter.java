package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.web.constants.SmartHttpHeaders;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.RequestTimestampException;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessBO;
import org.smartframework.cloud.examples.support.gateway.filter.access.ApiAccessContext;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求时间校验
 *
 * @author collin
 * @date 2021-07-17
 */
@Configuration
public class RequestTimestampCheckFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return Order.REQUEST_TIMESTAMP_CHECK;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiAccessBO apiAccessBO = ApiAccessContext.getContext();
        Long requestValidMillis = apiAccessBO.getRequestValidMillis();
        if (requestValidMillis == null || requestValidMillis <= 0) {
            return chain.filter(exchange);
        }

        String requestTimestampStr = WebUtil.getFromRequestHeader(exchange.getRequest(), SmartHttpHeaders.TIMESTAMP);
        if (StringUtils.isBlank(requestTimestampStr)) {
            throw new RequestTimestampException(GatewayReturnCodes.REQUEST_TIMESTAMP_MISSING);
        }
        if (!StringUtils.isNumeric(requestTimestampStr)) {
            throw new RequestTimestampException(GatewayReturnCodes.REQUEST_TIMESTAMP_FORMAT_INVALID);
        }
        if (Math.abs(System.currentTimeMillis() - Long.valueOf(requestTimestampStr)) < requestValidMillis) {
            throw new RequestTimestampException(GatewayReturnCodes.REQUEST_TIMESTAMP_ILLEGAL);
        }
        return chain.filter(exchange);
    }

}