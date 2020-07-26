package org.smartframework.cloud.examples.support.gateway.configure;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.filter.log.ApiLogDO;
import org.smartframework.cloud.examples.support.gateway.filter.log.LogUtil;
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
        RespVO<Base> respVO = new RespVO<>(ExceptionHandlerContext.transRespHead(throwable));
        String response = respVO.toString();
        printErrorLog(response);
        
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        return serverHttpResponse.writeWith(Mono.fromSupplier(() -> {
            return serverHttpResponse.bufferFactory().wrap(response.getBytes(StandardCharsets.UTF_8));
        }));
    }

    /**
     * 错误请求日志打印
     *
     * @param response
     */
    private void printErrorLog(String response) {
        ApiLogDO apiLogDO = LogUtil.getApiLogCache().get();
        if (apiLogDO != null) {
            apiLogDO.setCost(System.currentTimeMillis() - apiLogDO.getCost());
            apiLogDO.setResult(response);
            log.info("gateway.log.error=>{}", apiLogDO);
        }
    }

}