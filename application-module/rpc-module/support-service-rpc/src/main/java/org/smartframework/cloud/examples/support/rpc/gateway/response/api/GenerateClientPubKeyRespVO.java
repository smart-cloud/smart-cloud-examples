package org.smartframework.cloud.examples.support.rpc.gateway.response.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GenerateClientPubKeyRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌（登录校验前5分钟有效；登陆成功后7天有效）
     */
    private String token;

    /**
     * 服务端生成的公钥对应的系数，用于给客户端校验签名
     */
    private String pubKeyModulus;

    /**
     * 服务端生成的公钥对应的专用指数，用于给客户端校验签名
     */
    private String pubKeyExponent;

}