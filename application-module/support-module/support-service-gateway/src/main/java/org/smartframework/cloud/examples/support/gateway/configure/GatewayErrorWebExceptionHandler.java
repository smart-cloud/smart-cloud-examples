package org.smartframework.cloud.examples.support.gateway.configure;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.support.gateway.filter.log.ApiLogDO;
import org.smartframework.cloud.examples.support.gateway.filter.log.LogContext;
import org.smartframework.cloud.starter.web.exception.ExceptionHandlerContext;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局异常处理
 *
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
@Configuration
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        log.error("gateway.errorlog", throwable);
        Response<Base> response = new Response<>(ExceptionHandlerContext.transRespHead(throwable));
        String result = response.toString();
        printErrorLog(result);

        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverHttpResponse.writeWith(Mono.fromSupplier(() -> {
            return serverHttpResponse.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        }));
    }

    /**
     * 错误请求日志打印
     *
     * @param response
     */
    private void printErrorLog(String response) {
        ApiLogDO apiLogDO = LogContext.getApiLogBO();
        if (apiLogDO != null) {
            apiLogDO.setCost(System.currentTimeMillis() - apiLogDO.getCost());
            apiLogDO.setResult(response);
            log.info("gateway.log.error=>{}", apiLogDO);
        }
    }

}