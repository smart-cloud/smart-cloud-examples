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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.exception.DataValidateException;
import org.smartframework.cloud.exception.ServerException;
import org.smartframework.cloud.utility.RandomUtil;
import org.smartframework.cloud.utility.security.RsaUtil;
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

    private final RedissonClient redissonClient;

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

        RMapCache<String, SecurityKeyCache> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        authCache.put(RedisKeyHelper.getSecurityKey(token), securityKeyCache, RedisExpire.SECURITY_KEY_EXPIRE_SECONDS_NON_LOGIN, TimeUnit.SECONDS);

        return respVO;
    }

    public GenerateAesKeyRespVO generateAesKey(GenerateAesKeyReqVO req) throws InvalidKeySpecException, NoSuchAlgorithmException,
            DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // 1、解密客户端生成的公钥
        RMapCache<String, SecurityKeyCache> securityKeyMapCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        SecurityKeyCache securityKeyCache = securityKeyMapCache.get(RedisKeyHelper.getSecurityKey(req.getToken()));
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
        securityKeyMapCache.put(RedisKeyHelper.getSecurityKey(req.getToken()), securityKeyCache, RedisExpire.SECURITY_KEY_EXPIRE_SECONDS_NON_LOGIN, TimeUnit.SECONDS);

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