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

}