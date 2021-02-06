package org.smartframework.cloud.examples.mall.rpc.order.request.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 提交订单请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class SubmitOrderReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品信息
     */
    @NotEmpty
    @Valid
    private List<SubmitOrderProductInfoReqVO> products;

}