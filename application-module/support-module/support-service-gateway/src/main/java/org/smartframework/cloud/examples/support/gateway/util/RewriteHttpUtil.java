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

/**
 * 重写http上下文工具类
 *
 * @author collin
 * @date 2020-07-21
 */
public class RewriteHttpUtil {

    private RewriteHttpUtil() {
    }

    /**
     * 是否可读
     *
     * @param contentType
     * @return
     */
    public static boolean isReadable(MediaType contentType) {
        return contentType != null && contentType.includes(MediaType.APPLICATION_JSON);
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