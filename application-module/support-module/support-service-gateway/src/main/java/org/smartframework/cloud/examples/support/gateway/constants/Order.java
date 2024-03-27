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

/**
 * order
 *
 * @author collin
 * @date 2020-07-17
 */
public interface Order {

    /**
     * http请求重写
     */
    int REWRITE_HTTP = 1;
    /**
     * 请求日志order
     */
    int REQUEST_LOG = REWRITE_HTTP + 1;

    /**
     * 黑白名单
     */
    int BLACK_WHITE_LIST = REQUEST_LOG + 1;

    /**
     * api access注解全局过滤器order
     */
    int API_ACCESS = BLACK_WHITE_LIST + 1;

    /**
     * api access注解全局过滤器order
     */
    int REQUEST_TIMESTAMP_CHECK = API_ACCESS + 1;

    /**
     * api重复提交校验
     */
    int REPEAT_SUBMIT_CHECK = REQUEST_TIMESTAMP_CHECK + 1;

    /**
     * 接口安全（加解密、签名）
     */
    int DATA_SECURITY = REPEAT_SUBMIT_CHECK + 1;

    /**
     * 鉴权全局过滤器order
     */
    int AUTH_CHECK = DATA_SECURITY + 1;

}