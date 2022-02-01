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
package org.smartframework.cloud.examples.support.gateway.util;

import com.google.common.collect.Sets;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 重写http上下文
 *
 * @author collin
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
            MediaType.TEXT_XML);

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