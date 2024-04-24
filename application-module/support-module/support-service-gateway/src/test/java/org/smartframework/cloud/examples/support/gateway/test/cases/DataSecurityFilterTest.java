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
package org.smartframework.cloud.examples.support.gateway.test.cases;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.common.web.constants.SmartHttpHeaders;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.test.core.integration.WebReactiveIntegrationTest;
import io.github.smart.cloud.utility.JacksonUtil;
import io.github.smart.cloud.utility.NonceUtil;
import io.github.smart.cloud.utility.security.AesUtil;
import io.github.smart.cloud.utility.security.RsaUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.support.gateway.constants.DataSecurityConstants;
import org.smartframework.cloud.examples.support.gateway.service.api.SecurityApiService;
import org.smartframework.cloud.examples.support.gateway.service.rpc.UserRpcService;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.GetBodyDTO;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.GetDTO;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.PostBodyDTO;
import org.smartframework.cloud.examples.support.gateway.util.SignUtil;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

class DataSecurityFilterTest extends WebReactiveIntegrationTest {

    @Autowired
    private SecurityApiService securityApiService;
    @Autowired
    private UserRpcService userRpcService;

    static {
        System.setProperty("smart.uploadApiMeta", Boolean.TRUE.toString());
    }

    /**
     * http get url传参
     *
     * @throws Exception
     */
    @Test
    void testGet() throws Exception {
        // 1、获取客户端RSA公钥
        GenerateClientPubKeyRespVO clientPubKeyRespVO = generateClientPubKey();

        // 2、获取AES秘钥
        RSAPublicKey clientRsaPublicKey = RsaUtil.getRsaPublidKey(clientPubKeyRespVO.getPubKeyModulus(), clientPubKeyRespVO.getPubKeyExponent());

        KeyPair serverPubClientPriKeyPair = RsaUtil.generateKeyPair(DataSecurityConstants.RSA_KEY_SIZE);
        String cpubKeyModulus = RsaUtil.getModulus(serverPubClientPriKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(serverPubClientPriKeyPair);
        String encryptedAesKey = getEncryptedAesKey(clientPubKeyRespVO.getToken(), clientRsaPublicKey, cpubKeyModulus, cpubKeyExponent);

        RSAPrivateKey rsaPrivateKey2 = (RSAPrivateKey) serverPubClientPriKeyPair.getPrivate();
        String aesKey = RsaUtil.decryptString(rsaPrivateKey2, encryptedAesKey);

        // 3、登录，处理有效token
        String token = clientPubKeyRespVO.getToken();
        CacheUserInfoReqDTO cacheUserInfoReqDTO = new CacheUserInfoReqDTO();
        cacheUserInfoReqDTO.setToken(token);
        cacheUserInfoReqDTO.setUid(1L);
        cacheUserInfoReqDTO.setUsername("test_username");
        cacheUserInfoReqDTO.setRealName("test_name");
        cacheUserInfoReqDTO.setMobile("13112345686");
        cacheUserInfoReqDTO.setRoles(Collections.emptySet());
        cacheUserInfoReqDTO.setPermissions(Collections.emptySet());
        userRpcService.cacheUserInfo(cacheUserInfoReqDTO);

        // 4、测试
        GetDTO requestDto = new GetDTO();
        requestDto.setAge(13);
        requestDto.setName("test名字");
        requestDto.setHeight(180);

        Field[] fields = GetDTO.class.getDeclaredFields();
        String q = Arrays.stream(fields).map(field -> {
            field.setAccessible(true);
            try {
                return field.getName() + "=" + field.get(requestDto);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).reduce((a, b) -> a + "&" + b).get();

        // 4.1请求参数处理
        // 4.1.1AES加密
        q = AesUtil.encrypt(q, aesKey);

        // 4.1.2base64 encode
        q = Base64Utils.encodeToString(q.getBytes(StandardCharsets.UTF_8));

        Map<String, String> headers = new TreeMap<>();
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(SmartHttpHeaders.NONCE, String.valueOf(NonceUtil.nextId()));
        headers.put(SmartHttpHeaders.TOKEN, token);
        // 4.1.3签名
        String headerSignContent = JacksonUtil.toJson(headers);

        StringBuilder content = new StringBuilder(64);
        content.append(HttpMethod.GET);
        content.append(headerSignContent);
        content.append(q);

        headers.put(SmartHttpHeaders.SIGN, RsaUtil.sign(content.toString(), rsaPrivateKey2));

        Response<String> response = get("/gateway/api/test/get?q=" + q, headers, null, new TypeReference<Response<String>>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCode()).isNotBlank();
        Assertions.assertThat(response.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(response.getSign()).isNotBlank();
        Assertions.assertThat(response.getBody()).isNotBlank();
        // 4.2返回结果处理
        // 4.2.1验签
        String signContent = SignUtil.generateResponseSignContent(response.getNonce(), response.getTimestamp(), response.getBody());
        boolean checkSign = RsaUtil.checkSign(signContent, response.getSign(), clientRsaPublicKey);
        Assertions.assertThat(checkSign).isTrue();

        // 4.2.2base64 decode
        String baseDecodedBody = new String(Base64Utils.decodeFromString(response.getBody()));
        Assertions.assertThat(baseDecodedBody).isNotBlank();

        // 4.2.3AES解密
        String decryptedBody = AesUtil.decrypt(baseDecodedBody, aesKey);
        Assertions.assertThat(decryptedBody).isNotBlank();

        GetDTO result = JacksonUtil.parseObject(decryptedBody, GetDTO.class);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAge()).isEqualTo(requestDto.getAge());
        Assertions.assertThat(result.getName()).isEqualTo(requestDto.getName());
        Assertions.assertThat(result.getHeight()).isEqualTo(requestDto.getHeight() + 1);
    }

    /**
     * http body传参
     *
     * @throws Exception
     */
    @Test
    void testGetBody() throws Exception {
        // 1、获取客户端RSA公钥
        GenerateClientPubKeyRespVO clientPubKeyRespVO = generateClientPubKey();

        // 2、获取AES秘钥
        RSAPublicKey clientRsaPublicKey = RsaUtil.getRsaPublidKey(clientPubKeyRespVO.getPubKeyModulus(), clientPubKeyRespVO.getPubKeyExponent());

        KeyPair serverPubClientPriKeyPair = RsaUtil.generateKeyPair(DataSecurityConstants.RSA_KEY_SIZE);
        String cpubKeyModulus = RsaUtil.getModulus(serverPubClientPriKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(serverPubClientPriKeyPair);
        String encryptedAesKey = getEncryptedAesKey(clientPubKeyRespVO.getToken(), clientRsaPublicKey, cpubKeyModulus, cpubKeyExponent);

        RSAPrivateKey rsaPrivateKey2 = (RSAPrivateKey) serverPubClientPriKeyPair.getPrivate();
        String aesKey = RsaUtil.decryptString(rsaPrivateKey2, encryptedAesKey);

        // 3、登录，处理有效token
        String token = clientPubKeyRespVO.getToken();
        CacheUserInfoReqDTO cacheUserInfoReqDTO = new CacheUserInfoReqDTO();
        cacheUserInfoReqDTO.setToken(token);
        cacheUserInfoReqDTO.setUid(1L);
        cacheUserInfoReqDTO.setUsername("test_username");
        cacheUserInfoReqDTO.setRealName("test_name");
        cacheUserInfoReqDTO.setMobile("13112345686");
        cacheUserInfoReqDTO.setRoles(Collections.emptySet());
        cacheUserInfoReqDTO.setPermissions(Collections.emptySet());
        userRpcService.cacheUserInfo(cacheUserInfoReqDTO);

        // 4、测试
        GetBodyDTO requestDto = new GetBodyDTO();
        requestDto.setAge(13);
        requestDto.setName("test名字");
        requestDto.setHeight(180);

        // 4.1请求参数处理
        // 4.1.1AES加密
        String encryptedBody = AesUtil.encrypt(JacksonUtil.toJson(requestDto), aesKey);

        // 4.1.2base64 encode
        String base64EncryptedBody = Base64Utils.encodeToString(encryptedBody.getBytes(StandardCharsets.UTF_8));

        Map<String, String> headers = new TreeMap<>();
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(SmartHttpHeaders.NONCE, String.valueOf(NonceUtil.nextId()));
        headers.put(SmartHttpHeaders.TOKEN, token);
        // 4.1.3签名
        String headerSignContent = JacksonUtil.toJson(headers);

        StringBuilder content = new StringBuilder(64);
        content.append(HttpMethod.GET);
        content.append(headerSignContent);
        content.append(base64EncryptedBody);

        headers.put(SmartHttpHeaders.SIGN, RsaUtil.sign(content.toString(), rsaPrivateKey2));

        Response<String> response = get("/gateway/api/test/getBody", headers, null, base64EncryptedBody, new TypeReference<Response<String>>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCode()).isNotBlank();
        Assertions.assertThat(response.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(response.getSign()).isNotBlank();
        Assertions.assertThat(response.getBody()).isNotBlank();
        // 4.2返回结果处理
        // 4.2.1验签
        String signContent = SignUtil.generateResponseSignContent(response.getNonce(), response.getTimestamp(), response.getBody());
        boolean checkSign = RsaUtil.checkSign(signContent, response.getSign(), clientRsaPublicKey);
        Assertions.assertThat(checkSign).isTrue();

        // 4.2.2base64 decode
        String baseDecodedBody = new String(Base64Utils.decodeFromString(response.getBody()));
        Assertions.assertThat(baseDecodedBody).isNotBlank();

        // 4.2.3AES解密
        String decryptedBody = AesUtil.decrypt(baseDecodedBody, aesKey);
        Assertions.assertThat(decryptedBody).isNotBlank();

        GetBodyDTO result = JacksonUtil.parseObject(decryptedBody, GetBodyDTO.class);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAge()).isEqualTo(requestDto.getAge());
        Assertions.assertThat(result.getName()).isEqualTo(requestDto.getName());
        Assertions.assertThat(result.getHeight()).isEqualTo(requestDto.getHeight() + 2);
    }

    /**
     * post body传参
     *
     * @throws Exception
     */
    @Test
    void testPostBody() throws Exception {
        // 1、获取客户端RSA公钥
        GenerateClientPubKeyRespVO clientPubKeyRespVO = generateClientPubKey();

        // 2、获取AES秘钥
        RSAPublicKey clientRsaPublicKey = RsaUtil.getRsaPublidKey(clientPubKeyRespVO.getPubKeyModulus(), clientPubKeyRespVO.getPubKeyExponent());

        KeyPair serverPubClientPriKeyPair = RsaUtil.generateKeyPair(DataSecurityConstants.RSA_KEY_SIZE);
        String cpubKeyModulus = RsaUtil.getModulus(serverPubClientPriKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(serverPubClientPriKeyPair);
        String encryptedAesKey = getEncryptedAesKey(clientPubKeyRespVO.getToken(), clientRsaPublicKey, cpubKeyModulus, cpubKeyExponent);

        RSAPrivateKey rsaPrivateKey2 = (RSAPrivateKey) serverPubClientPriKeyPair.getPrivate();
        String aesKey = RsaUtil.decryptString(rsaPrivateKey2, encryptedAesKey);

        // 3、登录，处理有效token
        String token = clientPubKeyRespVO.getToken();
        CacheUserInfoReqDTO cacheUserInfoReqDTO = new CacheUserInfoReqDTO();
        cacheUserInfoReqDTO.setToken(token);
        cacheUserInfoReqDTO.setUid(1L);
        cacheUserInfoReqDTO.setUsername("test_username");
        cacheUserInfoReqDTO.setRealName("test_name");
        cacheUserInfoReqDTO.setMobile("13112345686");
        cacheUserInfoReqDTO.setRoles(Collections.emptySet());
        cacheUserInfoReqDTO.setPermissions(Collections.emptySet());
        userRpcService.cacheUserInfo(cacheUserInfoReqDTO);

        // 4、测试
        PostBodyDTO requestDto = new PostBodyDTO();
        requestDto.setId(13L);
        requestDto.setName("test名字");
        requestDto.setLength(15);

        // 4.1请求参数处理
        // 4.1.1AES加密
        String encryptedBody = AesUtil.encrypt(JacksonUtil.toJson(requestDto), aesKey);

        // 4.1.2base64 encode
        String base64EncryptedBody = Base64Utils.encodeToString(encryptedBody.getBytes(StandardCharsets.UTF_8));

        Map<String, String> headers = new TreeMap<>();
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(SmartHttpHeaders.NONCE, String.valueOf(NonceUtil.nextId()));
        headers.put(SmartHttpHeaders.TOKEN, token);
        // 4.1.3签名
        String headerSignContent = JacksonUtil.toJson(headers);

        StringBuilder content = new StringBuilder(64);
        content.append(HttpMethod.POST);
        content.append(headerSignContent);
        content.append(base64EncryptedBody);

        headers.put(SmartHttpHeaders.SIGN, RsaUtil.sign(content.toString(), rsaPrivateKey2));

        Response<String> response = post("/gateway/api/test/postBody", headers, base64EncryptedBody, new TypeReference<Response<String>>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCode()).isNotBlank();
        Assertions.assertThat(response.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(response.getSign()).isNotBlank();
        Assertions.assertThat(response.getBody()).isNotBlank();
        // 4.2返回结果处理
        // 4.2.1验签
        String signContent = SignUtil.generateResponseSignContent(response.getNonce(), response.getTimestamp(), response.getBody());
        boolean checkSign = RsaUtil.checkSign(signContent, response.getSign(), clientRsaPublicKey);
        Assertions.assertThat(checkSign).isTrue();

        // 4.2.2base64 decode
        String baseDecodedBody = new String(Base64Utils.decodeFromString(response.getBody()));
        Assertions.assertThat(baseDecodedBody).isNotBlank();

        // 4.2.3AES解密
        String decryptedBody = AesUtil.decrypt(baseDecodedBody, aesKey);
        Assertions.assertThat(decryptedBody).isNotBlank();

        PostBodyDTO result = JacksonUtil.parseObject(decryptedBody, PostBodyDTO.class);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(requestDto.getId());
        Assertions.assertThat(result.getName()).isEqualTo(requestDto.getName());
        Assertions.assertThat(result.getLength()).isEqualTo(requestDto.getLength() + 4);
    }

    /**
     * http body+url传参
     *
     * @throws Exception
     */
    @Test
    void testPostBody2() throws Exception {
        // 1、获取客户端RSA公钥
        GenerateClientPubKeyRespVO clientPubKeyRespVO = generateClientPubKey();

        // 2、获取AES秘钥
        RSAPublicKey clientRsaPublicKey = RsaUtil.getRsaPublidKey(clientPubKeyRespVO.getPubKeyModulus(), clientPubKeyRespVO.getPubKeyExponent());

        KeyPair serverPubClientPriKeyPair = RsaUtil.generateKeyPair(DataSecurityConstants.RSA_KEY_SIZE);
        String cpubKeyModulus = RsaUtil.getModulus(serverPubClientPriKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(serverPubClientPriKeyPair);
        String encryptedAesKey = getEncryptedAesKey(clientPubKeyRespVO.getToken(), clientRsaPublicKey, cpubKeyModulus, cpubKeyExponent);

        RSAPrivateKey rsaPrivateKey2 = (RSAPrivateKey) serverPubClientPriKeyPair.getPrivate();
        String aesKey = RsaUtil.decryptString(rsaPrivateKey2, encryptedAesKey);

        // 3、登录，处理有效token
        String token = clientPubKeyRespVO.getToken();
        CacheUserInfoReqDTO cacheUserInfoReqDTO = new CacheUserInfoReqDTO();
        cacheUserInfoReqDTO.setToken(token);
        cacheUserInfoReqDTO.setUid(1L);
        cacheUserInfoReqDTO.setUsername("test_username");
        cacheUserInfoReqDTO.setRealName("test_name");
        cacheUserInfoReqDTO.setMobile("13112345686");
        cacheUserInfoReqDTO.setRoles(Collections.emptySet());
        cacheUserInfoReqDTO.setPermissions(Collections.emptySet());
        userRpcService.cacheUserInfo(cacheUserInfoReqDTO);

        // 4、测试
        PostBodyDTO requestDto = new PostBodyDTO();
        requestDto.setId(13L);
        requestDto.setName("test名字");
        requestDto.setLength(15);

        // 4.1请求参数处理
        // 4.1.1AES加密
        String encryptedBody = AesUtil.encrypt(JacksonUtil.toJson(requestDto), aesKey);

        // 4.1.2base64 encode
        String base64EncryptedBody = Base64Utils.encodeToString(encryptedBody.getBytes(StandardCharsets.UTF_8));
        String desc = "三星mobile";
        String q = AesUtil.encrypt("desc=" + desc, aesKey);

        // 4.1.2base64 encode
        q = Base64Utils.encodeToString(q.getBytes(StandardCharsets.UTF_8));

        Map<String, String> headers = new TreeMap<>();
        headers.put(SmartHttpHeaders.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(SmartHttpHeaders.NONCE, String.valueOf(NonceUtil.nextId()));
        headers.put(SmartHttpHeaders.TOKEN, token);
        // 4.1.3签名
        String headerSignContent = JacksonUtil.toJson(headers);

        StringBuilder content = new StringBuilder(64);
        content.append(HttpMethod.POST);
        content.append(headerSignContent);
        content.append(q);
        content.append(base64EncryptedBody);

        headers.put(SmartHttpHeaders.SIGN, RsaUtil.sign(content.toString(), rsaPrivateKey2));

        Response<String> response = post("/gateway/api/test/postBody2?q=" + q, headers, base64EncryptedBody, new TypeReference<Response<String>>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCode()).isNotBlank();
        Assertions.assertThat(response.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(response.getSign()).isNotBlank();
        Assertions.assertThat(response.getBody()).isNotBlank();
        // 4.2返回结果处理
        // 4.2.1验签
        String signContent = SignUtil.generateResponseSignContent(response.getNonce(), response.getTimestamp(), response.getBody());
        boolean checkSign = RsaUtil.checkSign(signContent, response.getSign(), clientRsaPublicKey);
        Assertions.assertThat(checkSign).isTrue();

        // 4.2.2base64 decode
        String baseDecodedBody = new String(Base64Utils.decodeFromString(response.getBody()));
        Assertions.assertThat(baseDecodedBody).isNotBlank();

        // 4.2.3AES解密
        String decryptedBody = AesUtil.decrypt(baseDecodedBody, aesKey);
        Assertions.assertThat(decryptedBody).isNotBlank();

        PostBodyDTO result = JacksonUtil.parseObject(decryptedBody, PostBodyDTO.class);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(requestDto.getId());
        Assertions.assertThat(result.getName()).isEqualTo(requestDto.getName() + desc);
        Assertions.assertThat(result.getLength()).isEqualTo(requestDto.getLength() + 5);
    }

    /**
     * 获取客户端RSA公钥
     *
     * @return
     */
    private GenerateClientPubKeyRespVO generateClientPubKey() {
        GenerateClientPubKeyRespVO clientPubKeyRespVO = securityApiService.generateClientPubKey();
        Assertions.assertThat(clientPubKeyRespVO).isNotNull();
        Assertions.assertThat(clientPubKeyRespVO.getToken()).isNotBlank();
        Assertions.assertThat(clientPubKeyRespVO.getPubKeyExponent()).isNotBlank();
        Assertions.assertThat(clientPubKeyRespVO.getPubKeyModulus()).isNotBlank();

        return clientPubKeyRespVO;
    }

    /**
     * 获取AES秘钥
     *
     * @param token
     * @param clientRsaPublicKey
     * @param cpubKeyModulus
     * @param cpubKeyExponent
     * @return
     * @throws DecoderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    private String getEncryptedAesKey(String token, RSAPublicKey clientRsaPublicKey, String cpubKeyModulus, String cpubKeyExponent) throws DecoderException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        GenerateAesKeyReqVO generateAesKeyReqVO = new GenerateAesKeyReqVO();
        generateAesKeyReqVO.setToken(token);
        generateAesKeyReqVO.setEncryptedCpubKeyModulus(generateEncryptedCpubKey(clientRsaPublicKey, cpubKeyModulus));
        generateAesKeyReqVO.setEncryptedCpubKeyExponent(RsaUtil.encryptString(clientRsaPublicKey, StringUtils.reverse(cpubKeyExponent)));

        GenerateAesKeyRespVO generateAesKeyRespVO = securityApiService.generateAesKey(generateAesKeyReqVO);
        Assertions.assertThat(generateAesKeyRespVO).isNotNull();
        Assertions.assertThat(generateAesKeyRespVO.getEncryptedAesKey()).isNotBlank();

        return generateAesKeyRespVO.getEncryptedAesKey();
    }

    private static String[] generateEncryptedCpubKey(RSAPublicKey publicKey, String cpubKeyText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String[] encryptedCpubKey = new String[3];
        // 公钥明文被拆分后每一段的长度（因RSA加密字符串长度限制，前端传过来的明文被均分为3部分加密传输）
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