package org.smartframework.cloud.examples.system.test.module.mall;

import lombok.Getter;
import org.apache.commons.codec.DecoderException;
import org.assertj.core.api.Assertions;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.examples.system.test.module.support.gateway.api.SecurityApi;
import org.smartframework.cloud.utility.security.RsaUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

public final class TokenUtil {
    @Getter
    private static RSAPublicKey clientRSAPublicKey;

    public static String login() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
        RespVO<GenerateClientPubKeyRespVO> generateClientPubKeyRespVO = SecurityApi.generateClientPubKey();
        Assertions.assertThat(generateClientPubKeyRespVO).isNotNull();
        Assertions.assertThat(generateClientPubKeyRespVO.getHead()).isNotNull();
        Assertions.assertThat(generateClientPubKeyRespVO.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(generateClientPubKeyRespVO.getBody()).isNotNull();

        GenerateClientPubKeyRespVO clientPubKey = generateClientPubKeyRespVO.getBody();
        TokenUtil.clientRSAPublicKey = RsaUtil.getRSAPublidKey(clientPubKey.getPubKeyModulus(), clientPubKey.getPubKeyExponent());


        return null;
    }

}