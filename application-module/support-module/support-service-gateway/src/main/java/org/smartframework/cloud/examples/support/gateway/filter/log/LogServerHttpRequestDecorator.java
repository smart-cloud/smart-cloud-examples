package org.smartframework.cloud.examples.support.gateway.filter.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 包装ServerHttpRequest对象，获取请求body参数
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
public class LogServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private Flux<DataBuffer> body;

    LogServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        Flux<DataBuffer> flux = super.getBody();
        if (LogUtil.legalLogMediaTypes.contains(delegate.getHeaders().getContentType())) {
            body = flux.publishOn(Schedulers.single()).map(dataBuffer ->
                    LogUtil.chain(LogUtil.DataType.REQUEST, dataBuffer, LogUtil.getApiLogCache().get()));
        } else {
            body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

}