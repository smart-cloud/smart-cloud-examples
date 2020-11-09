package org.smartframework.cloud.examples.system.test.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.examples.system.test.module.basic.user.api.RegisterApi;
import org.smartframework.cloud.examples.system.test.module.support.gateway.api.SecurityApi;
import org.smartframework.cloud.utility.RandomUtil;
import org.smartframework.cloud.utility.security.RsaUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Slf4j
public final class TokenUtil {

    private static volatile Context context = null;

    private TokenUtil() {
    }

    public static Context getContext() {
        if (context == null) {
            synchronized (TokenUtil.class) {
                if (context == null) {
                    try {
                        context = login();
                    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | DecoderException
                            | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
                        log.error("fetch token fail!", e);
                    }
                }
            }
        }
        return context;
    }

    private static Context login() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, DecoderException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // 1、请求服务端生成RSA秘钥
        RespVO<GenerateClientPubKeyRespVO> generateClientPubKeyResult = SecurityApi.generateClientPubKey();
        Assertions.assertThat(generateClientPubKeyResult).isNotNull();
        Assertions.assertThat(generateClientPubKeyResult.getHead()).isNotNull();
        Assertions.assertThat(generateClientPubKeyResult.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(generateClientPubKeyResult.getBody()).isNotNull();

        GenerateClientPubKeyRespVO clientPubKey = generateClientPubKeyResult.getBody();
        RSAPublicKey clientRSAPublicKey = RsaUtil.getRSAPublidKey(clientPubKey.getPubKeyModulus(), clientPubKey.getPubKeyExponent());
        String token = clientPubKey.getToken();
        context = new Context();
        context.setClientRSAPublicKey(clientRSAPublicKey);
        context.setToken(token);

        // 2.1、客户端生成一对RSA公、私钥（PK2、SK2）
        KeyPair serverPubClientPriKeyPair = RsaUtil.generateKeyPair();
        //2.2、用PK1将PK2加密传给服务端
        GenerateAesKeyReqVO generateAesKeyReqVO = new GenerateAesKeyReqVO();
        generateAesKeyReqVO.setToken(token);

        String cpubKeyModulus = RsaUtil.getModulus(serverPubClientPriKeyPair);
        String cpubKeyExponent = RsaUtil.getPublicExponent(serverPubClientPriKeyPair);
        generateAesKeyReqVO.setEncryptedCpubKeyModulus(generateEncryptedCpubKey(clientRSAPublicKey, cpubKeyModulus));
        generateAesKeyReqVO.setEncryptedCpubKeyExponent(RsaUtil.encryptString(clientRSAPublicKey, StringUtils.reverse(cpubKeyExponent)));
        RespVO<GenerateAesKeyRespVO> generateAesKeyResult = SecurityApi.generateAesKey(generateAesKeyReqVO);
        Assertions.assertThat(generateAesKeyResult).isNotNull();
        Assertions.assertThat(generateAesKeyResult.getHead()).isNotNull();
        Assertions.assertThat(generateAesKeyResult.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(generateAesKeyResult.getBody()).isNotNull();
        // 2.3、解密AES key
        String aesKey = RsaUtil.decryptString(serverPubClientPriKeyPair.getPrivate(), generateAesKeyResult.getBody().getEncryptedAesKey());
        Assertions.assertThat(aesKey.length()).isEqualTo(8);

        context.setAesKey(aesKey);

        // 3、注册
        String mobile = RandomUtil.generateRandom(true, 11);
        UserInfoInsertReqVO userInfo = UserInfoInsertReqVO.builder()
                .mobile(mobile)
                .nickname("nickname_" + mobile)
                .realname("realname_" + mobile)
                .sex((byte) 1)
                .birthday(new Date())
                .channel((byte) 1)
                .build();

        LoginInfoInsertReqVO loginInfo = LoginInfoInsertReqVO.builder()
                .username("username" + mobile)
                .password("123456")
                .pwdState((byte) 1)
                .build();

        RegisterUserReqVO registerUserReqVO = RegisterUserReqVO.builder()
                .token(token)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .build();
        RespVO<RegisterUserRespVO> RegisterUserResult = RegisterApi.register(registerUserReqVO);
        Assertions.assertThat(RegisterUserResult).isNotNull();
        Assertions.assertThat(RegisterUserResult.getHead()).isNotNull();
        Assertions.assertThat(RegisterUserResult.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(RegisterUserResult.getBody()).isNotNull();

        context.setLoginRespVO(RegisterUserResult.getBody());

        return context;
    }

    private static String[] generateEncryptedCpubKey(RSAPublicKey publicKey, String cpubKeyText) throws InvalidKeyException,
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

    @Getter
    @Setter
    public static class Context {
        private RSAPublicKey clientRSAPublicKey;
        private String token;
        private String aesKey;
        private LoginRespVO loginRespVO;
    }

}