package org.smartframework.cloud.examples.mall.rpc.order.response.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

/**
 * 订单信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class OrderBillBaseRespVO extends BaseEntityRespVO {

    private static final long serialVersionUID = 1L;

    /**
     * 订单金额总金额
     */
    private Long amount;

    /**
     * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
     */
    private Byte payState;

    /**
     * 购买人id（demo_user库t_user_info表f_id）
     */
    private Long buyer;

}