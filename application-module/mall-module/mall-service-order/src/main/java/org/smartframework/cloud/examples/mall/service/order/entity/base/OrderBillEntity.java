package org.smartframework.cloud.examples.mall.service.order.entity.base;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 订单信息
 *
 * @author liyulin
 * @date 2019-11-09
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
	
    /** 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败） */
    @Column(name = "f_pay_state")     
	private Byte payState;
	
    /** 购买人id（demo_user库t_user_info表f_id） */
    @Column(name = "f_buyer")     
	private Long buyer;
	
	/** 表字段名 */
	public enum Columns {
	    /** 订单金额总金额 */
		amount,
	    /** 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败） */
		payState,
	    /** 购买人id（demo_user库t_user_info表f_id） */
		buyer;
	}

}