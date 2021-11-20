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
package org.smartframework.cloud.examples.support.gateway.http.codec;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstant;
import org.smartframework.cloud.utility.SerializingUtil;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProtostuffHttpMessageReader implements HttpMessageReader<Object> {

    private int maxInMemorySize = 256 * 1024;

    @Override
    public List<MediaType> getReadableMediaTypes() {
        return Collections.singletonList(ProtostuffConstant.PROTOBUF_MEDIA_TYPE);
    }

    @Override
    public boolean canRead(ResolvableType elementType, MediaType mediaType) {
        return mediaType != null && ProtostuffConstant.PROTOBUF_MEDIA_TYPE.isCompatibleWith(mediaType);
    }

    @Override
    public Flux<Object> read(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
        return Flux.from(readMono(elementType, message, hints));
    }

    @Override
    public Mono<Object> readMono(ResolvableType elementType, ReactiveHttpInputMessage message,
                                 Map<String, Object> hints) {
        return DataBufferUtils.join(message.getBody(), this.maxInMemorySize).map(buffer -> {
            Class<?> typeClass = getType(elementType.getType());
            Object result = null;
            try {
                result = SerializingUtil.deserialize(buffer.asInputStream(), typeClass);
            } catch (IOException e) {
                log.error("deserialize protostuff error", e);
            }

            return result;
        });
    }

    private Class<?> getType(Type type) {
        Class<?> c = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            c = (Class<?>) parameterizedType.getRawType();
        } else {
            c = (Class<?>) type;
        }
        return c;
    }

}