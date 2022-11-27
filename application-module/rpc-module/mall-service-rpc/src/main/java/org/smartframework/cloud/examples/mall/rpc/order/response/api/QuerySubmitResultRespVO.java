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
package org.smartframework.cloud.examples.mall.rpc.order.response.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 *
 * @author collin
 * @date 2021-02-07
 */
@Getter
@Setter
@ToString
public class QuerySubmitResultRespVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 金额总金额
     */
    private Long amount;

    /**
     * 订单状态（1：待扣减库存；2：扣减库存失败；3：抵扣优惠券失败；4：待付款；5：已取消；6：待发货；7：待收货；8：待评价，9：已完成）
     */
    private Byte orderStatus;

    /**
     * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
     */
    private Byte payStatus;

    /**
     * 运单信息
     */
    private List<OrderDeliveryRespVO> orderDeliveries;

}