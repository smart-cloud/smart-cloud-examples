package org.smartframework.cloud.examples.support.gateway.filter.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

/**
 * 打印请求、响应日志
 *
 * @author liyulin
 * @date 2020-07-17
 */
@Component
@Slf4j
public class GatewayLogFilter implements WebFilter, Ordered {

    @Override
    public int getOrder() {
        return Order.REQUEST_LOG;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        fillLog(exchange.getRequest());

        LogServerHttpRequestDecorator requestDecorator = new LogServerHttpRequestDecorator(exchange.getRequest());
        LogServerHttpResponseDecorator responseWrapper = new LogServerHttpResponseDecorator(exchange.getResponse());

        return chain.filter(exchange.mutate().request(requestDecorator).response(responseWrapper).build()).doFinally(signalType -> {
            if (SignalType.ON_ERROR.compareTo(signalType) != 0) {
                ApiLogDO apiLogDO = LogContext.getApiLogBO();
                apiLogDO.setCost(System.currentTimeMillis() - apiLogDO.getCost());
                if (log.isDebugEnabled()) {
                    log.debug("gateway.log=>{}", apiLogDO);
                } else if (apiLogDO.getUrl() != null && apiLogDO.getUrl().startsWith(GatewayConstants.GATEWAY_API_URL_PREFIX)) {
                    // gateway本身的接口打印
                    log.info("gateway.log=>{}", apiLogDO);
                }
            }
            LogContext.remove();
        });
    }

    private void fillLog(ServerHttpRequest request) {
        final String path = request.getURI().getPath();
        final String query = request.getURI().getQuery();

        ApiLogDO apiLogDO = new ApiLogDO();
        // 此处cost存储请求开始时间
        apiLogDO.setCost(System.currentTimeMillis());
        apiLogDO.setMethod(request.getMethod().name());
        apiLogDO.setUrl(path + (StringUtils.isBlank(query) ? "" : "?" + query));
        apiLogDO.setHead(request.getHeaders());
        LogContext.setContext(apiLogDO);
    }

}