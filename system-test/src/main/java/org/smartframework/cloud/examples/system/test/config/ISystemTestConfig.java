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
 * 系统测试配置
 *
 * @author collin
 * @date 2020-09-29
 */
public interface ISystemTestConfig {

    /**
     * 获取用户服务请求base url
     *
     * @return
     */
    String getUserBaseUrl();

    /**
     * 获取订单服务请求base url
     *
     * @return
     */
    String getOrderBaseUrl();

    /**
     * 获取商品服务请求base url
     *
     * @return
     */
    String getProductBaseUrl();

    /**
     * 获取网关服务请求base url
     *
     * @return
     */
    String getGatewayBaseUrl();

}