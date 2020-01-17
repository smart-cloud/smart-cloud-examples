package org.smartframework.cloud.examples.mall.rpc.product.response.rpc;

import java.util.List;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(description = "根据ids查询商品信息响应信息")
public class QryProductByIdsRespVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品信息")
	private List<QryProductByIdRespVO> productInfos;

}