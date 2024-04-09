package org.smartframework.cloud.examples.support.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * ip黑白名单配置
 *
 * @author collin
 * @date 2024-03-27
 */
@Getter
@Setter
@ToString
public class BlackWhiteListProperties {

    /**
     * 黑白名单是否可用
     */
    private boolean enable;
    /**
     * 黑名单
     */
    private IpList blackIpList = new IpList();
    /**
     * 白名单
     */
    private IpList whiteIpList = new IpList();

    @Getter
    @Setter
    public static class IpList {
        /**
         * 全局ip名单
         */
        private Set<String> ipList = new LinkedHashSet<>();

        /**
         * url名单<url, ip名单集合>
         */
        private Map<String, Set<String>> urlIpList = new LinkedHashMap<>();
    }

}