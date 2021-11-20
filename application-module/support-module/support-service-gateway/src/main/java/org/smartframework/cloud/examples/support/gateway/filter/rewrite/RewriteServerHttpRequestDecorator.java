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
package org.smartframework.cloud.examples.support.gateway.filter.rewrite;

import lombok.Getter;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * 包装ServerHttpRequest对象，获取请求body参数
 *
 * @author collin
 * @date 2020-07-21
 */
public class RewriteServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private transient Flux<DataBuffer> body;
    @Getter
    private transient String bodyStr;

    RewriteServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        Flux<DataBuffer> flux = super.getBody();
        if (RewriteHttpUtil.getLegalLogMediaTypes().contains(delegate.getHeaders().getContentType())) {
            flux.subscribe(buffer -> {
                byte[] bytes = RewriteHttpUtil.convert(buffer);
                bodyStr = new String(bytes, StandardCharsets.UTF_8);
                this.body = Flux.just(RewriteHttpUtil.convert(bytes));
            });
        } else {
            this.body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

}