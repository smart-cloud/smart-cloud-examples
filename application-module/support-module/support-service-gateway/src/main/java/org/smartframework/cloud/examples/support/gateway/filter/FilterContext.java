package org.smartframework.cloud.examples.support.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;

/**
 * 网关过滤器内部请求参数
 *
 * @author collin
 * @date 2021-07-21
 */
@Getter
@Setter
@Accessors(chain = true)
public class FilterContext {

    /**
     * token
     */
    private String token;
    /**
     * 接口注解信息
     */
    ApiAccessMetaCache apiAccessMetaCache;
    /**
     * 用于重复提交
     */
    private String urlMethod;

}