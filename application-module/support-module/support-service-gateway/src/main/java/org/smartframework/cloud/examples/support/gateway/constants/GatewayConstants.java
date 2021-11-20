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

import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;

/**
 * gateway常量
 *
 * @author collin
 * @date 2020-08-07
 */
public interface GatewayConstants {

    /**
     * gateway接口url前缀
     */
    String GATEWAY_API_URL_PREFIX = "/gateway";
    /**
     * 过滤器上下文key
     */
    String FILTER_CONTEXT_KEY = FilterContext.class.getSimpleName();
    /**
     * http get、post（application/x-www-form-urlencoded）请求参数加密后的key名
     */
    String REQUEST_ENCRYPT_PARAM_NAME = "q";

}