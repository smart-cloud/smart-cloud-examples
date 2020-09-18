package org.smartframework.cloud.examples.support.gateway.filter.log;

import com.google.common.collect.Sets;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.smartframework.cloud.starter.core.constants.ProtostuffConstant;
import org.smartframework.cloud.starter.log.util.LogUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
public class LogContext {

    /**
     * 打印日志的http content-type类型
     */
    protected static final Set<MediaType> legalLogMediaTypes = Sets.newHashSet(MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            ProtostuffConstant.PROTOBUF_MEDIA_TYPE);

    /**
     * 存储临时日志
     */
    private static ThreadLocal<ApiLogDO> apiLogCache = new InheritableThreadLocal<>();

    private LogContext() {
    }

    public static void setContext(ApiLogDO apiLogDO) {
        apiLogCache.set(apiLogDO);
    }

    public static ApiLogDO getApiLogBO() {
        return apiLogCache.get();
    }

    public static void remove() {
        apiLogCache.remove();
    }

    public static <T extends DataBuffer> T chain(DataType dataType, T buffer, ApiLogDO apiLogDO) {
        try (InputStream dataBuffer = buffer.asInputStream();){
            byte[] bytes = IOUtils.toByteArray(dataBuffer);
            if (apiLogDO != null) {
                String data = new String(bytes, StandardCharsets.UTF_8);
                // 请求数据
                if (dataType == DataType.REQUEST) {
                    apiLogDO.setArgs(data);
                }
                // 响应数据
                if (dataType == DataType.RESPONSE) {
                    // 超过长度的截掉
                    apiLogDO.setResult(LogUtil.truncate(data));
                }
            }

            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
            return (T) nettyDataBufferFactory.wrap(bytes);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 数据类型
     */
    static enum DataType {
        /**
         * 请求数据
         */
        REQUEST,
        /**
         * 响应数据
         */
        RESPONSE;
    }

}