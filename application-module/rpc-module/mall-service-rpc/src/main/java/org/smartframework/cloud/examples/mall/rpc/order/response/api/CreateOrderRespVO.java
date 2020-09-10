package org.smartframework.cloud.examples.mall.rpc.order.response.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 创建订单响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class CreateOrderRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 是否免单
     */
    private boolean free;

}