package org.smartframework.cloud.examples.system.test.config;

import lombok.experimental.UtilityClass;
import org.smartframework.cloud.examples.system.test.enums.SytemTestEnv;

import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class SystemTestConfig {

    /**
     * 当前环境
     */
    private static final SytemTestEnv CURRENT_ENV = SytemTestEnv.LOCAL;

    private static final Map<SytemTestEnv, ISystemTestConfig> CONFIG_ROUTE = new EnumMap<>(
            SytemTestEnv.class);

    static {
        CONFIG_ROUTE.put(SytemTestEnv.LOCAL, new SystemTestLocalConfig());
    }

    public static String getUserBaseUrl() {
        return CONFIG_ROUTE.get(CURRENT_ENV).getUserBaseUrl();
    }

    public static String getOrderBaseUrl() {
        return CONFIG_ROUTE.get(CURRENT_ENV).getOrderBaseUrl();
    }

    public static String getProductBaseUrl() {
        return CONFIG_ROUTE.get(CURRENT_ENV).getProductBaseUrl();
    }

    public static String getGatewayBaseUrl() {
        return CONFIG_ROUTE.get(CURRENT_ENV).getGatewayBaseUrl();
    }

}