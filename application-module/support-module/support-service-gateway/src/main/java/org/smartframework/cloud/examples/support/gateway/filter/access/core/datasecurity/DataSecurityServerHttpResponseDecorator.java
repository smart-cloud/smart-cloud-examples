/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import io.github.smart.cloud.api.core.annotation.enums.SignType;
import org.reactivestreams.Publisher;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.UnsupportedFunctionException;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 响应信息加密、签名
 *
 * @author collin
 * @date 2020-07-21
 */
public class DataSecurityServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private transient Publisher<? extends DataBuffer> body;

    DataSecurityServerHttpResponseDecorator(ServerHttpResponse response, boolean responseEncrypt, byte signType) {
        super(response);

        if ((responseEncrypt || signType == SignType.RESPONSE.getType() || signType == SignType.ALL.getType())
                && !RewriteHttpUtil.isSupported(super.getHeaders().getContentType())) {
            throw new UnsupportedFunctionException(GatewayReturnCodes.NOT_SUPPORT_DATA_SECURITY);
        }
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        DataBufferFactory dataBufferFactory = super.bufferFactory();
        if (body instanceof Mono) {
            ((Mono<DataBuffer>) body).subscribe(buffer -> {
                byte[] bytes = RewriteHttpUtil.convert(buffer);
                String bodyStr = new String(bytes, StandardCharsets.UTF_8);
                this.body = Mono.just(dataBufferFactory.wrap(bytes));
            });

            return super.writeWith(this.body);
        } else if (body instanceof Flux) {
            ((Flux<DataBuffer>) body).subscribe(buffer -> {
                byte[] bytes = RewriteHttpUtil.convert(buffer);
                String bodyStr = new String(bytes, StandardCharsets.UTF_8);
                this.body = Flux.just(dataBufferFactory.wrap(bytes));
            });
            return super.writeWith(this.body);
        }
        return super.writeWith(body);
    }

}