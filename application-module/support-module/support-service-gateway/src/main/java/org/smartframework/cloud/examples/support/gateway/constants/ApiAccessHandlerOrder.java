package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * 接口处理优先级（值越小，有效及越高）
 *
 * @author collin
 * @date 2021-06-28
 */
public final class ApiAccessHandlerOrder {

    /**
     * 接口安全（加解密、签名）
     */
    public static final int DATA_SECURITY = 1;
    /**
     * 接口鉴权
     */
    public static final int AUTH_CHECK = 2;
    /**
     * 重复提交校验
     */
    public static final int REPEAT_SUBMIT_CHECK = 3;

}