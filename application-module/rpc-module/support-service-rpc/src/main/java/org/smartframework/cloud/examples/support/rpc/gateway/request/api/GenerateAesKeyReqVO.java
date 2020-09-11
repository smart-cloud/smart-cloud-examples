package org.smartframework.cloud.examples.support.rpc.gateway.request.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GenerateAesKeyReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

    /**
     * 客户端生成的被（服务端公钥）加密的公钥对应的系数（备注：明文被均分为3部分）
     */
    @NotEmpty
    @Size(min = 3, max = 3)
    private String[] encryptedCpubKeyModulus;

    /**
     * 客户端生成的被（服务端公钥）加密的公钥对应的专用指数（备注：明文被均分为3部分）
     */
    @NotEmpty
    @Size(min = 3, max = 3)
    private String[] encryptedCpubKeyExponent;

}