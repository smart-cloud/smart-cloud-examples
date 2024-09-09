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
package org.smartframework.cloud.examples.mall.rpc.order.response.base;

import io.github.smart.cloud.common.pojo.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 订单信息
 *
 * @author collin
 * @date 2021-12-12
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@SuperBuilder
public class OrderBillBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** 订单号 */
	private String orderNo;
	
    /** 订单金额总金额 */
	private Long amount;
	
    /** 订单状态（1：待扣减库存；2：扣减库存失败；3：抵扣优惠券失败；4：待付款；5：已取消；6：待发货；7：待收货；8：待评价，9：已完成） */
	private Byte status;
	
    /** 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败） */
	private Byte payState;
	
    /** 购买人id（demo_user库t_user_info表f_id） */
	private Long buyer;
	
}