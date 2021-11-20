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
package org.smartframework.cloud.examples.support.gateway.cache;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 签名相关key
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
public class SecurityKeyCache extends Base {

    /**
     * 客户端生成的公钥对应的系数，用于客户端签名校验
     */
    private String cpubKeyModulus;

    /**
     * 客户端生成的公钥对应的专用指数，用于客户端签名校验
     */
    private String cpubKeyExponent;

    /**
     * 服务端生成的私钥对应的系数，用于给客户端数据加密
     */
    private String spriKeyModulus;

    /**
     * 服务端生成的私钥对应的专用指数，用于给客户端数据加密
     */
    private String spriKeyExponent;

    /**
     * AES加密key
     */
    private String aesKey;

}