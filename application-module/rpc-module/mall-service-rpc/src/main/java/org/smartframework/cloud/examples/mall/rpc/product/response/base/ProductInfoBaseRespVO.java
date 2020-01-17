package org.smartframework.cloud.examples.mall.rpc.product.response.base;

import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

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
@ApiModel(description = "商品信息")
public class ProductInfoBaseRespVO extends BaseEntityRespVO {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称")
	private String name;
	
    @ApiModelProperty(value = "销售价格（单位：万分之一元）")
	private Long sellPrice;
	
    @ApiModelProperty(value = "库存")
	private Long stock;
	
}