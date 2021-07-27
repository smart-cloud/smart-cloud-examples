package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import org.reactivestreams.Publisher;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 响应信息加密、签名
 *
 * @author liyulin
 * @date 2020-07-21
 */
public class DataSecurityServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private transient Publisher<? extends DataBuffer> body;

    DataSecurityServerHttpResponseDecorator(ServerHttpResponse delegate, boolean responseEncrypt, byte signType) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        // TODO:
//        final MediaType contentType = super.getHeaders().getContentType();
//        if (RewriteHttpUtil.getLegalLogMediaTypes().contains(contentType)) {
//            if (body instanceof Mono) {
//                ((Mono<DataBuffer>) body).subscribe(buffer -> {
//                    byte[] bytes = RewriteHttpUtil.convert(buffer);
//                    String bodyStr = new String(bytes, StandardCharsets.UTF_8);
//                    this.body = Mono.just(RewriteHttpUtil.convert(bytes));
//                });
//
//                return super.writeWith(this.body);
//            } else if (body instanceof Flux) {
//                ((Flux<DataBuffer>) body).subscribe(buffer -> {
//                    byte[] bytes = RewriteHttpUtil.convert(buffer);
//                    String bodyStr = new String(bytes, StandardCharsets.UTF_8);
//                    this.body = Flux.just(RewriteHttpUtil.convert(bytes));
//                });
//                return super.writeWith(this.body);
//            }
//        }
        return super.writeWith(body);
    }

}