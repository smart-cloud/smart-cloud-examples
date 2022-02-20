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
import lombok.experimental.SuperBuilder;

/**
 * 运单信息
 *
 * @author collin
 * @date 2021-12-12
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class OrderDeliveryInfoBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** 订单号（t_order_bill表f_order_no） */
	private String orderNo;
	
    /** 购买的商品id（demo_product库t_product_info表f_id） */
	private Long productInfoId;
	
    /** 商品名称 */
	private String productName;
	
    /** 商品购买价格（单位：万分之一元） */
	private Long price;
	
    /** 购买数量 */
	private Integer buyCount;
	
}