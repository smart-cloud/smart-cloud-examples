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

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;

/**
 * 重写http上下文工具类
 *
 * @author collin
 * @date 2020-07-21
 */
public class RewriteHttpUtil {

    /**
     * 打印日志的http content-type类型
     */
    private static final Set<MediaType> LEGAL_LOG_MEDIA_TYPES = new HashSet<>(8);

    static {
        LEGAL_LOG_MEDIA_TYPES.add(MediaType.APPLICATION_XML);
        LEGAL_LOG_MEDIA_TYPES.add(MediaType.APPLICATION_JSON);
        LEGAL_LOG_MEDIA_TYPES.add(MediaType.APPLICATION_JSON_UTF8);
        LEGAL_LOG_MEDIA_TYPES.add(MediaType.TEXT_PLAIN);
        LEGAL_LOG_MEDIA_TYPES.add(MediaType.TEXT_XML);
    }

    public static Set<MediaType> getLegalLogMediaTypes() {
        return LEGAL_LOG_MEDIA_TYPES;
    }

    private RewriteHttpUtil() {
    }

    /**
     * DataBuffer转byte[]
     *
     * @param dataBuffer
     * @return
     */
    public static byte[] convert(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        try {
            dataBuffer.read(bytes);
        } finally {
            DataBufferUtils.release(dataBuffer);
        }
        return bytes;
    }

}