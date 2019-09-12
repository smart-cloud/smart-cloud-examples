package org.smartframework.cloud.examples.mall.service.rpc.product.request.api;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "分页查询商品信息请求参数")
public class PageProductReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称（模糊匹配）")
	private String name;

}