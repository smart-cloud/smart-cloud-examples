package org.smartframework.cloud.examples.support.gateway.service.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.examples.support.gateway.bo.SecurityKeyBO;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.smartframework.cloud.utility.RandomUtil;
import org.smartframework.cloud.utility.security.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author liyulin
 * @date 2020-09-10
 */
@Service
@Slf4j
public class SecurityApiService {

    @Autowired
    private RedissonClient redissonClient;

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
        SecurityKeyBO securityKeyBO = new SecurityKeyBO();
        securityKeyBO.setSpriKeyModulus(RsaUtil.getModulus(clientPubServerPriKeyPair));
        securityKeyBO.setSpriKeyExponent(RsaUtil.getPrivateExponent(clientPubServerPriKeyPair));

        RMapCache<String, SecurityKeyBO> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        authCache.put(RedisKeyHelper.getSecurityKey(token), securityKeyBO, RedisExpire.SECURITY_KEY_EXPIRE_MILLIS_NON_LOGIN, TimeUnit.SECONDS);

        return respVO;
    }

    public GenerateAesKeyRespVO generateAesKey(GenerateAesKeyReqVO req) throws InvalidKeySpecException, NoSuchAlgorithmException,
            DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // 1、解密客户端生成的公钥
        RMapCache<String, SecurityKeyBO> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        SecurityKeyBO securityKeyBO = authCache.get(RedisKeyHelper.getSecurityKey(req.getToken()));
        if (securityKeyBO == null) {
            throw new ServerException(GatewayReturnCodes.TOKEN_EXPIRED_BEFORE_LOGIN);
        }

        RSAPrivateKey rsaPrivateKey = RsaUtil.getRSAPrivateKey(securityKeyBO.getSpriKeyModulus(), securityKeyBO.getSpriKeyExponent());

        String cpubKeyModulus = decryptStringByJs(rsaPrivateKey, req.getEncryptedCpubKeyModulus());
        String cpubKeyExponent = RsaUtil.decryptStringByJs(rsaPrivateKey, req.getEncryptedCpubKeyExponent());
        // 2、cache to redis
        securityKeyBO.setCpubKeyModulus(cpubKeyModulus);
        securityKeyBO.setCpubKeyExponent(cpubKeyExponent);
        String aesKey = RandomUtil.generateRandom(false, 8);
        securityKeyBO.setAesKey(aesKey);
        authCache.put(RedisKeyHelper.getSecurityKey(req.getToken()), securityKeyBO, RedisExpire.SECURITY_KEY_EXPIRE_MILLIS_NON_LOGIN, TimeUnit.SECONDS);

        // 3、加密aes key
        // 客户端生成的公钥
        RSAPublicKey rsaPublicKey = RsaUtil.getRSAPublidKey(cpubKeyModulus, cpubKeyExponent);
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