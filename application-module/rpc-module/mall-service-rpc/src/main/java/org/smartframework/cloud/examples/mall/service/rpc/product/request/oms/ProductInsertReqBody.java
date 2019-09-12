package org.smartframework.cloud.examples.mall.service.rpc.product.request.oms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品新增请求参数")
public class ProductInsertReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称", required = true)
	@Size(max = 100)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）", required = true)
	@Min(100)
	@NotNull
	private Long sellPrice;

	@ApiModelProperty(value = "库存", required = true)
	@Min(1)
	@NotNull
	private Long stock;

}