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

/**
 * 系统测试url配置
 *
 * @author collin
 * @date 2019-07-12
 */
public class SystemTestLocalConfig extends AbstractSystemTestConfig implements ISystemTestConfig {

    private static final boolean GATEWAY = true;
    private static final String GATEWAY_BASE_URL = "http://localhost:80/";

    @Override
    public String getUserBaseUrl() {
        return getBaseUrl("http://localhost:20031/");
    }

    @Override
    public String getOrderBaseUrl() {
        return getBaseUrl("http://localhost:20011/");
    }

    @Override
    public String getProductBaseUrl() {
        return getBaseUrl("http://localhost:20021/");
    }

    @Override
    public boolean isGateway() {
        return GATEWAY;
    }

    @Override
    public String getGatewayBaseUrl() {
        return GATEWAY_BASE_URL;
    }

}