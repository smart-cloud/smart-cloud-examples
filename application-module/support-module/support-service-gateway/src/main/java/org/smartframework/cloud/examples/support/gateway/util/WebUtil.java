package org.smartframework.cloud.examples.support.gateway.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author liyulin
 * @date 2020-09-10
 */
public final class WebUtil {

    /**
     * 从请求头获取信息
     *
     * @param request
     * @param name
     * @return
     */
    public static String getFromRequestHeader(ServerHttpRequest request, String name) {
        String value = request.getHeaders().getFirst(name);
        if (StringUtils.isBlank(value)) {
            // 如果请求头中不包含授权信息则从Query中获取参数
            value = request.getQueryParams().getFirst(name.toLowerCase());
        }
        return value;
    }

}