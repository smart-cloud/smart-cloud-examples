package org.smartframework.cloud.examples.mall.service.rpc.order.request.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建订单请求参数")
public class CreateOrderReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品信息", required = true)
	@NotEmpty
	private List<@Valid CreateOrderProductInfoReqBody> products;

}