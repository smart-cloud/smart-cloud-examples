package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * 接口处理优先级（值越小，有效及越高）
 *
 * @author collin
 * @date 2021-06-28
 */
public interface ApiAccessHandlerOrder {

    /**
     * 接口安全（加解密、签名）
     */
    int DATA_SECURITY = 1;
    /**
     * 接口鉴权
     */
    int AUTH_CHECK = DATA_SECURITY + 1;
    /**
     * 重复提交校验
     */
    int REPEAT_SUBMIT_CHECK = AUTH_CHECK + 1;

}