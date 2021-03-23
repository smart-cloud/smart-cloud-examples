package org.smartframework.cloud.examples.mall.rpc.order.response.base;

import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 订单信息
 *
 * @author liyulin
 * @date 2021-03-23
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class OrderBillBaseRespVO extends BaseEntityRespVO {

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