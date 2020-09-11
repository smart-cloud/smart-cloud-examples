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
    int API_ACCESS = Ordered.HIGHEST_PRECEDENCE + 1;

    /**
     * token校验全局过滤器order
     */
    int TOKEN_CHECK = Ordered.HIGHEST_PRECEDENCE + 2;

    /**
     * api重复提交校验
     */
    int REPEAT_SUBMIT_CHECK = Ordered.HIGHEST_PRECEDENCE + 3;

}