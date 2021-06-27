package org.smartframework.cloud.examples.mall.rpc.order.response.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.BaseEntityResponse;

/**
 * 运单信息
 *
 * @author liyulin
 * @date 2021-03-23
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