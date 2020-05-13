package org.smartframework.cloud.examples.support.gateway.http.codec;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.reactivestreams.Publisher;
import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstant;
import org.smartframework.cloud.starter.rpc.feign.protostuff.SerializingUtil;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;

import reactor.core.publisher.Mono;

public class ProtostuffHttpMessageWriter implements HttpMessageWriter<Object>{

	@Override
	public List<MediaType> getWritableMediaTypes() {
		return Collections.singletonList(ProtostuffConstant.PROTOBUF_MEDIA_TYPE);
	}

	@Override
	public boolean canWrite(ResolvableType elementType, MediaType mediaType) {
		return mediaType != null && ProtostuffConstant.PROTOBUF_MEDIA_TYPE.isCompatibleWith(mediaType);
	}

	@Override
	public Mono<Void> write(Publisher<? extends Object> inputStream, ResolvableType elementType, MediaType mediaType,
			ReactiveHttpOutputMessage message, Map<String, Object> hints) {
		return Mono.from(inputStream).flatMap(form -> {
			byte[] data = SerializingUtil.serialize(form);
			ByteBuffer byteBuffer = ByteBuffer.wrap(data);
			DataBuffer buffer = message.bufferFactory().wrap(byteBuffer); // wrapping only, no allocation
			message.getHeaders().setContentLength(byteBuffer.remaining());
			return message.writeWith(Mono.just(buffer));
		});
	}
	
}