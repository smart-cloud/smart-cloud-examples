package org.smartframework.cloud.examples.mall.rpc.order.request.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建订单请求参数")
public class CreateOrderReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品信息", required = true)
	@NotEmpty
	private List<@Valid CreateOrderProductInfoReqVO> products;

}