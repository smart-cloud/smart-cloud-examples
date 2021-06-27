package org.smartframework.cloud.examples.support.gateway.constants;

import org.springframework.core.Ordered;

/**
 * order
 *
 * @author liyulin
 * @date 2020-07-17
 */
public interface Order {

    /**
     * 请求日志order
     */
    int REQUEST_LOG = Ordered.HIGHEST_PRECEDENCE;

    /**
     * api access注解全局过滤器order
     */
    int API_ACCESS = REQUEST_LOG + 1;

    /**
     * 接口安全（加解密、签名）
     */
    int DATA_SECURITY = API_ACCESS + 1;

    /**
     * 鉴权全局过滤器order
     */
    int AUTH_CHECK = DATA_SECURITY + 1;

    /**
     * api重复提交校验
     */
    int REPEAT_SUBMIT_CHECK = AUTH_CHECK + 1;

}