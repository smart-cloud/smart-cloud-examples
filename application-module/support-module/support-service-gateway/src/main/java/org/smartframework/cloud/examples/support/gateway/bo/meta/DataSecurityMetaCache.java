package org.smartframework.cloud.examples.support.gateway.bo.meta;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 接口安全（加解密、签名）meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
public class DataSecurityMetaCache extends Base {

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