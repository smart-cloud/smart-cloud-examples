package org.smartframework.cloud.examples.mall.rpc.product.request.oms;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 商品新增请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class ProductInsertReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @Size(max = 100)
    @NotBlank
    private String name;

    /**
     * 销售价格（单位：万分之一元）
     */
    @Min(100)
    @NotNull
    private Long sellPrice;

    /**
     * 库存
     */
    @Min(1)
    @NotNull
    private Long stock;

}