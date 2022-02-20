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
package org.smartframework.cloud.examples.system.test.util;

import io.github.smart.cloud.common.web.constants.SmartHttpHeaders;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.UUID;

/**
 * http header工具类
 *
 * @author collin
 * @date 2020-10-10
 */
public class HttpHeaderUtil {

    public static Header[] build() {
        Header[] headers = new Header[3];
        headers[0] = new BasicHeader(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers[1] = new BasicHeader(SmartHttpHeaders.NONCE, UUID.randomUUID().toString());
        headers[2] = new BasicHeader(SmartHttpHeaders.TOKEN, TokenUtil.getContext().getToken());
        return headers;
    }

}