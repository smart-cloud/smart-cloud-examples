package org.smartframework.cloud.examples.mall.rpc.enums.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
 *
 * @author liyulin
 * @date 2019-04-16
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayStateEnum {

    /**
     * 待支付
     */
    PENDING_PAY((byte) 1),
    /**
     * 支付成功
     */
    PAY_SUCCESS((byte) 2),
    /**
     * 支付失败
     */
    PAY_FAIL((byte) 3),
    /**
     * 待退款
     */
    PENDING_REFUND((byte) 4),
    /**
     * 退款成功
     */
    PENDING_SUCCESS((byte) 5),
    /**
     * 退款失败
     */
    PENDING_FAIL((byte) 6);

    private byte value;

}