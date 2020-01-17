package org.smartframework.cloud.examples.mall.rpc.product.request.oms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品修改请求参数")
public class ProductUpdateReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private Long id;

	@ApiModelProperty(value = "商品名称", required = true)
	@Size(max = 100)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）")
	@Min(100)
	private Long sellPrice;

	@ApiModelProperty(value = "库存")
	@Min(1)
	private Long stock;

}