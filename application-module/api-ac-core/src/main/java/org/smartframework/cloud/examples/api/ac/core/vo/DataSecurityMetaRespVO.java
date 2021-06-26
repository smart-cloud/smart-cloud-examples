package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 接口安全（加解密、签名）meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DataSecurityMetaRespVO extends Base {

    /**
     * 请求参数是否需要解密
     */
    private boolean requestDecrypt;

    /**
     * 响应信息是否需要加密
     */
    private boolean responseEncrypt;

    /**
     * 接口签名类型
     */
    private byte sign;

}