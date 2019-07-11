package org.smartframework.cloud.examples.mall.service.rpc.order.response.base;

import java.math.BigInteger;

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
@ApiModel(description = "订单信息响应信息")
public class OrderBillBaseRespBody extends BaseEntityRespBody {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单金额总金额")
	private BigInteger amount;

	@ApiModelProperty(value = "支付状态（1：待支付；2：支付成功；3：支付失败）")
	private Integer payState;

	@ApiModelProperty(value = "退款状态（1：无需退款；2：待退款；3：退款失败；4：退款成功）")
	private Integer refundState;

	@ApiModelProperty(value = "购买人id（demo_user库t_user_info表f_id）")
	private BigInteger buyer;

}