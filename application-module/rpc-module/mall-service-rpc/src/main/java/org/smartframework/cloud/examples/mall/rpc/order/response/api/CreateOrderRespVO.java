package org.smartframework.cloud.examples.mall.rpc.order.response.api;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建订单响应信息")
public class CreateOrderRespVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "是否免单")
	private boolean free;

}