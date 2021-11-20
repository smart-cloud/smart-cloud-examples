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
package org.smartframework.cloud.examples.mall.rpc.enums.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态（1：待扣减库存；2：扣减库存失败；3：抵扣优惠券失败；4：待付款；5：已取消；6：待发货；7：待收货；8：待评价，9：已完成）
 *
 * @author collin
 * @date 2021-02-07
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {

    /**
     * 待扣减库存
     */
    DEDUCT_STOCK_TODO((byte) 1),
    /**
     * 扣减库存失败
     */
    DEDUCT_STOCK_FAIL((byte) 2),
    /**
     * 抵扣优惠券失败
     */
    DEDUCT_COUPON_FAIL((byte) 3),
    /**
     * 待付款
     */
    PAY_TODO((byte) 4),
    /**
     * 已取消
     */
    CANCEL((byte) 5),
    /**
     * 待发货
     */
    SHIP_TODO((byte) 6),
    /**
     * 待收货
     */
    RECEIPT_TODO((byte) 7),
    /**
     * 待评价
     */
    REMARK_TODO((byte) 8),
    /**
     * 已完成
     */
    COMPLETED((byte) 9);

    private byte status;

}