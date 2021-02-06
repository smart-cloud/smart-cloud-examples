package org.smartframework.cloud.examples.mall.rpc.order.response.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.List;

/**
 * 订单详情
 *
 * @author collin
 * @date 2021-02-07
 */
@Getter
@Setter
public class QuerySubmitResultRespVO extends Base {

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