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
package org.smartframework.cloud.examples.common.config.constants;

/**
 * 数据源名称
 *
 * @author collin
 * @date 2020-09-28
 */
public interface DataSourceName {

    /**
     * 权限主库
     */
    String BASIC_AUTH_MASTER = "basic-auth-master";
    /**
     * 权限从库
     */
    String BASIC_AUTH_SLAVE = "basic-auth-slave";
    /**
     * 用户主库
     */
    String BASIC_USER_MASTER = "basic-user-master";
    /**
     * 用户从库
     */
    String BASIC_USER_SLAVE = "basic-user-slave";
    /**
     * 商品主库
     */
    String MALL_PRODUCT_MASTER = "mall-product-master";
    /**
     * 商品从库
     */
    String MALL_PRODUCT_SLAVE = "mall-product-slave";

}