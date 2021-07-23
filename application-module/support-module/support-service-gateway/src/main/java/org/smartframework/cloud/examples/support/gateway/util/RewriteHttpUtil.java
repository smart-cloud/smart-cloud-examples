package org.smartframework.cloud.examples.support.gateway.util;

import com.google.common.collect.Sets;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstant;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 重写http上下文
 *
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
public class RewriteHttpUtil {

    /**
     * 打印日志的http content-type类型
     */
    private static final Set<MediaType> LEGAL_LOG_MEDIA_TYPES = Sets.newHashSet(MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            ProtostuffConstant.PROTOBUF_MEDIA_TYPE);

    public static Set<MediaType> getLegalLogMediaTypes() {
        return LEGAL_LOG_MEDIA_TYPES;
    }

    private RewriteHttpUtil() {
    }

    public static <T extends DataBuffer> byte[] convert(T buffer) {
        try (InputStream dataBuffer = buffer.asInputStream();) {
            return IOUtils.toByteArray(dataBuffer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T extends DataBuffer> T convert(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
        return (T) nettyDataBufferFactory.wrap(bytes);
    }

}