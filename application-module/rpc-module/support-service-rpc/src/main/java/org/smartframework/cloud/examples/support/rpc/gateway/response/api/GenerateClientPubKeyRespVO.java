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
package org.smartframework.cloud.examples.support.rpc.gateway.response.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GenerateClientPubKeyRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌（登录校验前5分钟有效；登陆成功后7天有效）
     */
    private String token;

    /**
     * 服务端生成的公钥对应的系数，用于给客户端校验签名
     */
    private String pubKeyModulus;

    /**
     * 服务端生成的公钥对应的专用指数，用于给客户端校验签名
     */
    private String pubKeyExponent;

}