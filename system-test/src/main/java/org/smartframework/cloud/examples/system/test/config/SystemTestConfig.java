/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.system.test.config;

import lombok.experimental.UtilityClass;
import org.smartframework.cloud.examples.system.test.enums.SytemTestEnv;

import java.util.EnumMap;
import java.util.Map;

/**
 * 系统测试配置
 *
 * @author collin
 * @date 2019-07-12
 */
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