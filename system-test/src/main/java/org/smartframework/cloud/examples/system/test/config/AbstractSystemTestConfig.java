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
 * 系统测试配置抽象类
 *
 * @author collin
 * @date 2020-09-29
 */
public abstract class AbstractSystemTestConfig {

    /**
     * 获取base url
     *
     * @param singleBaseUrl 单体服务base url
     * @return
     */
    protected String getBaseUrl(String singleBaseUrl) {
        if (isGateway()) {
            return getGatewayBaseUrl();
        } else {
            return singleBaseUrl;
        }
    }

    /**
     * 是否走网关
     *
     * @return
     */
    public abstract boolean isGateway();

    /**
     * 网关地址前缀
     *
     * @return
     */
    public abstract String getGatewayBaseUrl();

}