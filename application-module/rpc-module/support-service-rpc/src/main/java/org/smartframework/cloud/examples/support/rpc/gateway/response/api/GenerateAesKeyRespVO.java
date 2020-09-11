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
public class GenerateAesKeyRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 被客户端公钥加密的AES加解密key
     */
    private String encryptedAesKey;

}