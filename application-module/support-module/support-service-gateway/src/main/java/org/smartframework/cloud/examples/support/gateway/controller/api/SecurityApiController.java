package org.smartframework.cloud.examples.support.gateway.controller.api;

import org.apache.commons.codec.DecoderException;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.service.api.SecurityApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("gateway/api/security")
@Validated
public class SecurityApiController {

    @Autowired
    private SecurityApiService securityApiService;

    /**
     * 生成客户端公钥
     *
     * @return
     */
    @PostMapping("generateClientPubKey")
    public RespVO<GenerateClientPubKeyRespVO> generateClientPubKey() {
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
    public RespVO<GenerateAesKeyRespVO> generateAesKey(@RequestBody @Valid GenerateAesKeyReqVO req) throws BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException, DecoderException, NoSuchPaddingException,
            InvalidKeyException, InvalidKeySpecException {
        return RespUtil.success(securityApiService.generateAesKey(req));
    }

}