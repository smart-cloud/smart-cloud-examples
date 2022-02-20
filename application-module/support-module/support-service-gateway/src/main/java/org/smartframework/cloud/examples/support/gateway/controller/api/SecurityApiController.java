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
package org.smartframework.cloud.examples.support.gateway.controller.api;

import io.github.smart.cloud.api.core.annotation.RequireTimestamp;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.smartframework.cloud.examples.support.gateway.service.api.SecurityApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 安全（秘钥相关）
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@Validated
@RestController
@RequestMapping("gateway/api/security")
@RequiredArgsConstructor
public class SecurityApiController {

    private final SecurityApiService securityApiService;

    /**
     * 生成客户端公钥
     *
     * @return
     */
    @PostMapping("generateClientPubKey")
    @RequireTimestamp
    public Response<GenerateClientPubKeyRespVO> generateClientPubKey() {
        return RespUtil.success(securityApiService.generateClientPubKey());
    }

    /**
     * 生成AES秘钥
     *
     * @param req
     * @return
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws DecoderException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    @PostMapping("generateAesKey")
    @RequireTimestamp
    public Response<GenerateAesKeyRespVO> generateAesKey(@RequestBody @Valid GenerateAesKeyReqVO req) throws BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException, DecoderException, NoSuchPaddingException,
            InvalidKeyException, InvalidKeySpecException {
        return RespUtil.success(securityApiService.generateAesKey(req));
    }

}