package org.smartframework.cloud.examples.support.gateway.http.codec;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstant;
import org.smartframework.cloud.starter.rpc.feign.protostuff.SerializingUtil;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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