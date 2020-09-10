package org.smartframework.cloud.examples.mall.rpc.product.request.oms;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 逻辑删除请求字段
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class ProductDeleteReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @NotNull
    @Min(1)
    private Long id;

}