package org.smartframework.cloud.examples.mall.service.rpc.product.request.oms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "逻辑删除请求字段")
public class ProductDeleteReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private Long id;

}