package org.smartframework.cloud.examples.support.gateway.constants;

import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;

/**
 * gateway常量
 *
 * @author liyulin
 * @date 2020-08-07
 */
public interface GatewayConstants {

    /**
     * gateway接口url前缀
     */
    String GATEWAY_API_URL_PREFIX = "/gateway";
    /**
     * 过滤器上下文key
     */
    String FILTER_CONTEXT_KEY = FilterContext.class.getSimpleName();

}