package org.smartframework.cloud.examples.support.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 黑白名单配置
 *
 * @author collin
 * @date 2024-03-27
 */
@Getter
@Setter
@ToString
public class BlackWhiteListProperties {

    /**
     * 黑名单<url, 黑名单集合>
     */
    private Map<String, Set<String>> blackList = new LinkedHashMap<>();
    /**
     * 白名单<url, 白名单集合>
     */
    private Map<String, Set<String>> whiteList = new LinkedHashMap<>();

}