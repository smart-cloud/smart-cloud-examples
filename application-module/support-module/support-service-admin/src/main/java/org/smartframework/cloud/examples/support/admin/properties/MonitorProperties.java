package org.smartframework.cloud.examples.support.admin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控配置属性
 *
 * @author collin
 * @date 2023-12-05
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "smart.monitor")
public class MonitorProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProxyProperties proxy = new ProxyProperties();
    /**
     * 通知配置
     */
    private Map<String, RobotProperties> robots = new HashMap<>();

}