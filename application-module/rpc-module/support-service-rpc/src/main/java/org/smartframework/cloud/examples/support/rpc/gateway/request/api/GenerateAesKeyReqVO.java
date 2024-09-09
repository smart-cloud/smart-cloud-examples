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
package org.smartframework.cloud.examples.support.rpc.gateway.request.api;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 生成AES秘钥请求参数
 *
 * @author collin
 * @date 2020-09-11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GenerateAesKeyReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

    /**
     * 客户端生成的被（服务端公钥）加密的公钥对应的系数（备注：明文被均分为3部分）
     */
    @NotEmpty
    @Size(min = 3, max = 3)
    private String[] encryptedCpubKeyModulus;

    /**
     * 客户端生成的被（服务端公钥）加密的公钥对应的专用指数
     */
    @NotBlank
    private String encryptedCpubKeyExponent;

}