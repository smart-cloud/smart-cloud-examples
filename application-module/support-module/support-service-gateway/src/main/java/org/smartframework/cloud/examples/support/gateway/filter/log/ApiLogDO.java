package org.smartframework.cloud.examples.support.gateway.filter.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @author liyulin
 * @date 2020-07-17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiLogDO implements Serializable {

    /**
     * 请求路径
     */
    private String url;
    /**
     * http请求方式
     */
    private String method;
    /**
     * http头部数据
     */
    private String head;
    /**
     * url参数
     */
    private String queryParams;
    /**
     * body部分请求体参数
     */
    private Object args;
    /**
     * 请求结果
     */
    private String result;
    /**
     * 花费时间（毫秒）
     */
    private long cost;

}