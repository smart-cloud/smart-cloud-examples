package org.smartframework.cloud.examples.support.admin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 代理配置属性
 *
 * @author collin
 * @date 2023-03-07
 */
@Getter
@Setter
@ToString
public class ProxyProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理ip
     */
    private String host = "";

    /**
     * 代理端口
     */
    private Integer port = null;

}