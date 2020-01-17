package org.smartframework.cloud.examples.mall.rpc.product.response.api;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ApiModel(description = "分页查询商品信息响应信息")
public class PageProductRespVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	private long id;
	
	@ApiModelProperty(value = "商品名称")
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）")
	private long sellPrice;

	@ApiModelProperty(value = "库存")
	private long stock;

}