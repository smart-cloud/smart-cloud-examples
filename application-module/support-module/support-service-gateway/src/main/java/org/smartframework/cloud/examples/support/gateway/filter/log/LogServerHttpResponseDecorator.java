package org.smartframework.cloud.examples.support.gateway.filter.log;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 包装ServerHttpResponse，获取响应结果
 *
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
public class LogServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    LogServerHttpResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        final MediaType contentType = super.getHeaders().getContentType();
        if (LogUtil.legalLogMediaTypes.contains(contentType)) {
            if (body instanceof Mono) {
                final Mono<DataBuffer> monoBody = (Mono<DataBuffer>) body;
                return super.writeWith(monoBody.publishOn(Schedulers.single())
                        .map(dataBuffer
                                -> LogUtil.chain(LogUtil.DataType.RESPONSE, dataBuffer, LogUtil.getApiLogCache().get())));
            } else if (body instanceof Flux) {
                final Flux<DataBuffer> monoBody = (Flux<DataBuffer>) body;
                return super.writeWith(monoBody.publishOn(Schedulers.single())
                        .map(dataBuffer
                                -> LogUtil.chain(LogUtil.DataType.RESPONSE, dataBuffer, LogUtil.getApiLogCache().get())));
            }
        }
        return super.writeWith(body);
    }

}