package org.smartframework.cloud.examples.mall.service.rpc.order.request.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建订单商品请求参数")
public class CreateOrderProductInfoReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private Long productId;

	@ApiModelProperty(value = "购买数量", required = true)
	@NotNull
	@Min(1)
	private Integer buyCount;

}