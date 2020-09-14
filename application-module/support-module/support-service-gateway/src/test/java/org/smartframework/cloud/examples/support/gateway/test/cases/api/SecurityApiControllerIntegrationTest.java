package org.smartframework.cloud.examples.support.gateway.test.cases.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodeEnum;
import org.smartframework.cloud.examples.support.gateway.service.api.SecurityApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
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

/**
 * @author liyulin
 * @date 2020-09-11
 */
@Slf4j
public class SecurityApiControllerIntegrationTest extends WebReactiveIntegrationTest {

    @Autowired
    private SecurityApiService securityApiService;

    @Test
    public void testGenerateClientPubKey() throws Exception {
        RespVO<GenerateClientPubKeyRespVO> result = post("/gateway/api/security/generateClientPubKey", null, new TypeReference<RespVO<GenerateClientPubKeyRespVO>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(result.getBody().getToken()).isNotBlank();
        Assertions.assertThat(result.getBody().getPubKeyModulus()).isNotBlank();
        Assertions.assertThat(result.getBody().getPubKeyExponent()).isNotBlank();
    }

    @Test
    public void testGenerateAesKey() throws Exception {
        // 客户端公钥（校验签名）、服务端私钥
        GenerateClientPubKeyRespVO generateClientPubKeyRespVO = securityApiService.generateClientPubKey();

        // 1、生成密钥对
        // 客户端私钥、服务端公钥
        KeyPair clientPriServerPubKeyPair = null;
        try {
            clientPriServerPubKeyPair = RsaUtil.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(GatewayReturnCodeEnum.GENERATE_RSAKEY_FAIL);
        }

        GenerateAesKeyReqVO generateAesKeyReqVO = new GenerateAesKeyReqVO();
        generateAesKeyReqVO.setToken(generateClientPubKeyRespVO.getToken());

        // 客户端使用的公钥
        RSAPublicKey publicKey = RsaUtil.getRSAPublidKey(generateClientPubKeyRespVO.getPubKeyModulus(), generateClientPubKeyRespVO.getPubKeyExponent());
        String cpubKeyModulus = RsaUtil.getModulus(clientPriServerPubKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(clientPriServerPubKeyPair);

        generateAesKeyReqVO.setEncryptedCpubKeyModulus(generateEncryptedCpubKey(publicKey, cpubKeyModulus));
        generateAesKeyReqVO.setEncryptedCpubKeyExponent(RsaUtil.encryptString(publicKey, StringUtils.reverse(cpubKeyExponent)));

        RespVO<GenerateAesKeyRespVO> result = post("/gateway/api/security/generateAesKey", generateAesKeyReqVO, new TypeReference<RespVO<GenerateAesKeyRespVO>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(result.getBody().getEncryptedAesKey()).isNotBlank();

        // 解密AES key
        String aesKey = RsaUtil.decryptString(clientPriServerPubKeyPair.getPrivate(), result.getBody().getEncryptedAesKey());
        Assertions.assertThat(aesKey.length()).isEqualTo(8);
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