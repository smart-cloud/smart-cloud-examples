package org.smartframework.cloud.examples.mall.rpc.order.request.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 创建订单请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class CreateOrderReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品信息
     */
    @NotEmpty
    private List<@Valid CreateOrderProductInfoReqVO> products;

}