package org.smartframework.cloud.examples.mall.service.rpc.order.response.base;

import org.smartframework.cloud.common.pojo.dto.BaseEntityRespBody;

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
@ApiModel(description = "运单信息")
public class OrderDeliveryInfoBaseRespBody extends BaseEntityRespBody {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属订单（t_order_bill表f_id）")
	private Long orderBillId;
	
    @ApiModelProperty(value = "购买的商品id（demo_product库t_product_info表f_id）")
	private Long productInfoId;
	
    @ApiModelProperty(value = "商品名称")
	private String productName;
	
    @ApiModelProperty(value = "商品购买价格（单位：万分之一元）")
	private Long price;
	
    @ApiModelProperty(value = "购买数量")
	private Integer buyCount;
	
}