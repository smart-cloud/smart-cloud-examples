package org.smartframework.cloud.examples.mall.service.order.entity.base;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 订单信息
 *
 * @author liyulin
 * @date 2019年3月31日下午4:26:15
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_order_bill")
public class OrderBillEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 订单金额总金额 */
	@Column(name = "f_amount")
	private Long amount;

	/** 支付状态（1：待支付；2：支付成功；3：支付失败） */
	@Column(name = "f_pay_state")
	private Byte payState;

	/** 退款状态（1：无需退款；2：待退款；3：退款失败；4：退款成功） */
	@Column(name = "f_refund_state")
	private Byte refundState;

	/** 购买人id（demo_user库t_user_info表f_id） */
	@Column(name = "f_buyer")
	private Long buyer;

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum Columns {
		/** 订单金额总金额 */
		AMOUNT("amount"),
		/** 支付状态 */
		PAY_STATE("payState"),
		/** 库存 */
		REFUND_STATE("refundState"),
		/** 购买人id */
		BUYER("buyer");

		private String property;
	}

}