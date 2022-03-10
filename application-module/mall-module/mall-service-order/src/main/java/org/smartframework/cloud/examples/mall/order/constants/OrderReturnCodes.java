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
package org.smartframework.cloud.examples.mall.order.constants;

/**
 * 订单服务状态码
 *
 * @author collin
 * @date 2019-04-16
 */
public interface OrderReturnCodes {

    /**
     * 库存更新失败
     */
    String UPDATE_STOCK_FAIL = "200001";
    /**
     * 商品不存在
     */
    String PRODUCT_NOT_EXIST = "200002";

}