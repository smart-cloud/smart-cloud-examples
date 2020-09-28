package org.smartframework.cloud.examples.mall.order.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

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
@TableName("t_order_bill")
public class OrderBillEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单金额总金额
     */
    @TableField(value = "f_amount")
    private Long amount;

    /**
     * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
     */
    @TableField(value = "f_pay_state")
    private Byte payState;

    /**
     * 购买人id（demo_user库t_user_info表f_id）
     */
    @TableField(value = "f_buyer")
    private Long buyer;

}