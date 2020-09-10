package org.smartframework.cloud.examples.mall.rpc.order.request.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 创建订单商品请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class CreateOrderProductInfoReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @NotNull
    @Min(1)
    private Long productId;

    /**
     * 购买数量
     */
    @NotNull
    @Min(1)
    private Integer buyCount;

}