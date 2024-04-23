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
package org.smartframework.cloud.examples.support.gateway.constants;

import io.github.smart.cloud.common.pojo.Response;

/**
 * 返回体字段名
 *
 * @author collin
 * @date 2024-04-23
 * @see {@link Response}
 */
public class ResponseFields {

    /**
     * @see {@link Response#nonce}
     */
    public static final String NONCE = "nonce";
    /**
     * @see {@link Response#timestamp}
     */
    public static final String TIMESTAMP = "timestamp";
    /**
     * @see {@link Response#body}
     */
    public static final String BODY = "body";
    /**
     * @see {@link Response#sign}
     */
    public static final String SIGN = "sign";

}