package org.smartframework.cloud.examples.support.gateway.filter.access.core;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.web.constants.SmartHttpHeaders;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.RequestTimestampException;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.filter.access.AbstractFilter;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 请求时间校验
 *
 * @author collin
 * @date 2021-07-17
 */
@Component
public class RequestTimestampCheckFilter extends AbstractFilter {

    @Override
    public int getOrder() {
        return Order.REQUEST_TIMESTAMP_CHECK;
    }

    @Override
    protected Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain, FilterContext filterContext) {
        Long requestValidMillis = filterContext.getApiAccessMetaCache().getRequestValidMillis();
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