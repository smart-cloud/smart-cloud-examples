package org.smartframework.cloud.examples.support.gateway.bo;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 签名相关key
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class SecurityKeyBO extends Base {

    /**
     * 客户端生成的公钥对应的系数，用于客户端签名校验
     */
    private String cpubKeyModulus;

    /**
     * 客户端生成的公钥对应的专用指数，用于客户端签名校验
     */
    private String cpubKeyExponent;

    /**
     * 服务端生成的私钥对应的系数，用于给客户端数据加密
     */
    private String spriKeyModulus;

    /**
     * 服务端生成的私钥对应的专用指数，用于给客户端数据加密
     */
    private String spriKeyExponent;

    /**
     * AES加密key
     */
    private String aesKey;

}