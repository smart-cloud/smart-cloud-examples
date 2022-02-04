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
package org.smartframework.cloud.examples.support.gateway.test.cases.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.constants.CommonReturnCodes;
import org.smartframework.cloud.common.web.constants.SmartHttpHeaders;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.service.api.SecurityApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.exception.ServerException;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;
import org.smartframework.cloud.utility.security.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author collin
 * @date 2020-09-11
 */
@Slf4j
class SecurityApiControllerIntegrationTest extends WebReactiveIntegrationTest {

    @Autowired
    private SecurityApiService securityApiService;

    @Test
    void testGenerateClientPubKey() throws Exception {
        Map<String, String> headers = new HashMap<>(1);
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));

        Response<GenerateClientPubKeyRespVO> result = post("/gateway/api/security/generateClientPubKey", headers, null, new TypeReference<Response<GenerateClientPubKeyRespVO>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody().getToken()).isNotBlank();
        Assertions.assertThat(result.getBody().getPubKeyModulus()).isNotBlank();
        Assertions.assertThat(result.getBody().getPubKeyExponent()).isNotBlank();
    }

    @Test
    void testGenerateAesKey() throws Exception {
        // 客户端公钥（校验签名）、服务端私钥
        GenerateClientPubKeyRespVO generateClientPubKeyRespVO = securityApiService.generateClientPubKey();

        // 1、生成密钥对
        // 客户端私钥、服务端公钥
        KeyPair clientPriServerPubKeyPair = null;
        try {
            clientPriServerPubKeyPair = RsaUtil.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(GatewayReturnCodes.GENERATE_RSAKEY_FAIL);
        }

        GenerateAesKeyReqVO generateAesKeyReqVO = new GenerateAesKeyReqVO();
        generateAesKeyReqVO.setToken(generateClientPubKeyRespVO.getToken());

        // 客户端使用的公钥
        RSAPublicKey publicKey = RsaUtil.getRsaPublidKey(generateClientPubKeyRespVO.getPubKeyModulus(), generateClientPubKeyRespVO.getPubKeyExponent());
        String cpubKeyModulus = RsaUtil.getModulus(clientPriServerPubKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(clientPriServerPubKeyPair);

        generateAesKeyReqVO.setEncryptedCpubKeyModulus(generateEncryptedCpubKey(publicKey, cpubKeyModulus));
        generateAesKeyReqVO.setEncryptedCpubKeyExponent(RsaUtil.encryptString(publicKey, StringUtils.reverse(cpubKeyExponent)));

        Map<String, String> headers = new HashMap<>(1);
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));

        Response<GenerateAesKeyRespVO> result = post("/gateway/api/security/generateAesKey", headers, generateAesKeyReqVO, new TypeReference<Response<GenerateAesKeyRespVO>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody().getEncryptedAesKey()).isNotBlank();

        // 解密AES key
        String aesKey = RsaUtil.decryptString(clientPriServerPubKeyPair.getPrivate(), result.getBody().getEncryptedAesKey());
        Assertions.assertThat(aesKey).hasSize(8);
    }

    private String[] generateEncryptedCpubKey(RSAPublicKey publicKey, String cpubKeyText) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String[] encryptedCpubKey = new String[3];
        // 公钥明文被拆分后每一段的长度
        int averageLen = cpubKeyText.length() / encryptedCpubKey.length;
        for (int i = 0, size = encryptedCpubKey.length; i < size; i++) {
            if (i < size - 1) {
                encryptedCpubKey[i] = RsaUtil.encryptString(publicKey, StringUtils.reverse(cpubKeyText.substring(i * averageLen, (i + 1) * averageLen)));
            } else {
                encryptedCpubKey[i] = RsaUtil.encryptString(publicKey, StringUtils.reverse(cpubKeyText.substring(i * averageLen, cpubKeyText.length())));
            }

        }
        return encryptedCpubKey;
    }

}