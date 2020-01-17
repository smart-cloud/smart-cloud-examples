package org.smartframework.cloud.examples.mall.rpc.order.response.base;

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
@ApiModel(description = "订单信息")
public class OrderBillBaseRespVO extends BaseEntityRespVO {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单金额总金额")
	private Long amount;
	
    @ApiModelProperty(value = "支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）")
	private Byte payState;
	
    @ApiModelProperty(value = "购买人id（demo_user库t_user_info表f_id）")
	private Long buyer;
	
}