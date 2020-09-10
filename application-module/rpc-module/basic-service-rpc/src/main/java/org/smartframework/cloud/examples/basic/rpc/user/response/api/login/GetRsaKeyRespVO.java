package org.smartframework.cloud.examples.basic.rpc.user.response.api.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 获取rsa key响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetRsaKeyRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问token
     */
    private String token;

    /**
     * 签名的modulus
     */
    private String signModules;

    /**
     * 签名的key
     */
    private String signKey;

    /**
     * 校验签名的modulus
     */
    private String checkSignModulus;

    /**
     * 校验签名的key
     */
    private String checkSignKey;

}