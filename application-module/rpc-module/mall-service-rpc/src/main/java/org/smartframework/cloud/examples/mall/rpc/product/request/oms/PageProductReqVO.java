package org.smartframework.cloud.examples.mall.rpc.product.request.oms;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "分页查询商品信息请求参数")
public class PageProductReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称")
	private String name;

}