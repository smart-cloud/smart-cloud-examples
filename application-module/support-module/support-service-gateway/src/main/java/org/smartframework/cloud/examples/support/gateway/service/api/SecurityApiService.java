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
package org.smartframework.cloud.examples.support.gateway.service.api;

import io.github.smart.cloud.exception.DataValidateException;
import io.github.smart.cloud.exception.ServerException;
import io.github.smart.cloud.utility.RandomUtil;
import io.github.smart.cloud.utility.security.RsaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.RedisTtl;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 安全
 *
 * @author collin
 * @date 2020-09-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityApiService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public GenerateClientPubKeyRespVO generateClientPubKey() {
        // 1、生成密钥对
        // 客户端公钥（校验签名）、服务端私钥（签名）
        KeyPair clientPubServerPriKeyPair = null;

        try {
            clientPubServerPriKeyPair = RsaUtil.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(GatewayReturnCodes.GENERATE_RSAKEY_FAIL);
        }

        GenerateClientPubKeyRespVO respVO = new GenerateClientPubKeyRespVO();
        respVO.setPubKeyModulus(RsaUtil.getModulus(clientPubServerPriKeyPair));
        respVO.setPubKeyExponent(RsaUtil.getPublicExponent(clientPubServerPriKeyPair));
        String token = generateToken();
        respVO.setToken(token);

        // 2、cache to redis
        SecurityKeyCache securityKeyCache = new SecurityKeyCache();
        securityKeyCache.setSpriKeyModulus(RsaUtil.getModulus(clientPubServerPriKeyPair));
        securityKeyCache.setSpriKeyExponent(RsaUtil.getPrivateExponent(clientPubServerPriKeyPair));

        redisTemplate.opsForValue().set(RedisKeyHelper.getSecurityKey(token), securityKeyCache, RedisTtl.SECURITY_KEY_NON_LOGIN, TimeUnit.MILLISECONDS);

        return respVO;
    }

    public GenerateAesKeyRespVO generateAesKey(GenerateAesKeyReqVO req) throws InvalidKeySpecException, NoSuchAlgorithmException,
            DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // 1、解密客户端生成的公钥
        String cacheKey = RedisKeyHelper.getSecurityKey(req.getToken());
        SecurityKeyCache securityKeyCache = (SecurityKeyCache) redisTemplate.opsForValue().get(cacheKey);
        if (securityKeyCache == null) {
            throw new DataValidateException(GatewayReturnCodes.TOKEN_EXPIRED_BEFORE_LOGIN);
        }

        RSAPrivateKey rsaPrivateKey = RsaUtil.getRsaPrivateKey(securityKeyCache.getSpriKeyModulus(), securityKeyCache.getSpriKeyExponent());

        String cpubKeyModulus = decryptStringByJs(rsaPrivateKey, req.getEncryptedCpubKeyModulus());
        String cpubKeyExponent = RsaUtil.decryptStringByJs(rsaPrivateKey, req.getEncryptedCpubKeyExponent());
        // 2、cache to redis
        securityKeyCache.setCpubKeyModulus(cpubKeyModulus);
        securityKeyCache.setCpubKeyExponent(cpubKeyExponent);
        String aesKey = RandomUtil.generateRandom(false, 8);
        securityKeyCache.setAesKey(aesKey);
        redisTemplate.opsForValue().set(cacheKey, securityKeyCache, RedisTtl.SECURITY_KEY_NON_LOGIN, TimeUnit.MILLISECONDS);

        // 3、加密aes key
        // 客户端生成的公钥
        RSAPublicKey rsaPublicKey = RsaUtil.getRsaPublidKey(cpubKeyModulus, cpubKeyExponent);
        String encryptedAesKey = RsaUtil.encryptString(rsaPublicKey, aesKey);
        return new GenerateAesKeyRespVO(encryptedAesKey);
    }

    private String decryptStringByJs(RSAPrivateKey rsaPrivateKey, String[] encryptedCpubKeys) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, DecoderException {
        // 因RSA加密字符串长度限制，前端传过来的明文被均分为3部分加密传输
        StringBuilder cpubKeyModulus = new StringBuilder(256);
        for (String encryptedCpubKey : encryptedCpubKeys) {
            cpubKeyModulus.append(RsaUtil.decryptStringByJs(rsaPrivateKey, encryptedCpubKey));
        }

        return cpubKeyModulus.toString();
    }


    /**
     * 生成token
     *
     * @return
     */
    private static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}