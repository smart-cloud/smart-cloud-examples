package org.smartframework.cloud.examples.support.gateway.filter.log;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.web.pojo.LogAspectDO;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpResponseDecorator;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
        ServerHttpRequest request = exchange.getRequest();

        LogAspectDO logAspectDO = new LogAspectDO();
        // 此处cost存储请求开始时间
        long startTime = System.currentTimeMillis();
        logAspectDO.setMethod(request.getMethod().name());
        logAspectDO.setUrl(request.getURI().getPath());
        logAspectDO.setQueryParams(request.getURI().getQuery());
        logAspectDO.setHead(request.getHeaders());
        if (request instanceof RewriteServerHttpRequestDecorator) {
            RewriteServerHttpRequestDecorator rewriteServerHttpRequest = (RewriteServerHttpRequestDecorator) request;
            logAspectDO.setArgs(rewriteServerHttpRequest.getBodyStr());
        }

        return chain.filter(exchange).doFinally(signalType -> {
            ServerHttpResponse response = exchange.getResponse();
            if (response instanceof RewriteServerHttpResponseDecorator) {
                RewriteServerHttpResponseDecorator rewriteServerHttpResponse = (RewriteServerHttpResponseDecorator) exchange.getResponse();
                logAspectDO.setResult(rewriteServerHttpResponse.getBodyStr());
            }
            logAspectDO.setCost(System.currentTimeMillis() - startTime);

            if (SignalType.ON_ERROR.compareTo(signalType) != 0) {
                if (log.isDebugEnabled()) {
                    log.debug("gateway.log=>{}", logAspectDO);
                } else if (logAspectDO.getUrl() != null && logAspectDO.getUrl().startsWith(GatewayConstants.GATEWAY_API_URL_PREFIX)) {
                    // gateway本身的接口打印
                    log.info("gateway.log=>{}", logAspectDO);
                }
            } else {
                log.error("gateway.error=>{}", logAspectDO);
            }
        });
    }

}