package org.smartframework.cloud.examples.support.gateway.filter.access;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;

/**
 * smart cloud自定义注解处理时需要的对应入参，供后续filter使用
 *
 * @author liyulin
 * @date 2020-09-08
 */
@Getter
@Setter
@Accessors(chain = true)
public class ApiAccessBO {

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

    /**
     * 请求有效间隔
     */
    private Long requestValidMillis;

}