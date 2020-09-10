package org.smartframework.cloud.examples.support.gateway.filter.log;

import com.google.common.collect.Lists;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author collin
 * @date 2020-07-21
 */
@Slf4j
public class LogUtil {

    /**
     * 打印日志的http content-type类型
     */
    public static final List<MediaType> legalLogMediaTypes = Lists.newArrayList(MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML);

    /**
     * 存储临时日志
     */
    @Getter
    private static ThreadLocal<ApiLogDO> apiLogCache = new InheritableThreadLocal<>();

    private LogUtil() {
    }

    public static ApiLogDO getApiLogBO() {
        ApiLogDO apiLogDO = apiLogCache.get();
        if (null != apiLogDO) {
            return apiLogDO;
        }
        apiLogDO = new ApiLogDO();
        apiLogCache.set(apiLogDO);

        return apiLogDO;
    }

    public static <T extends DataBuffer> T chain(DataType dataType, T buffer, ApiLogDO apiLogDO) {
        try {
            InputStream dataBuffer = buffer.asInputStream();
            byte[] bytes = IOUtils.toByteArray(dataBuffer);
            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));

            String data = new String(bytes, StandardCharsets.UTF_8);
            // 请求数据
            if (dataType == DataType.REQUEST) {
                apiLogDO.setArgs(data);
            }
            // 响应数据
            else if (data != null) {
                // 超过长度的截掉
                apiLogDO.setResult(data.length() <= 1024 ? data : data.substring(0, 1024));
            }

            DataBufferUtils.release(buffer);
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