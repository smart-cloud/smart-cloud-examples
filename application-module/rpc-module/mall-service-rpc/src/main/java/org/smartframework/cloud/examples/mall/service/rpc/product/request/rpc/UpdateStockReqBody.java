package org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(description = "更新库存请求参数")
public class UpdateStockReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private List<@Valid UpdateStockItem> items;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class UpdateStockItem extends BaseDto {

		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value = "商品id", required = true)
		@NotNull
		@Min(1)
		private long id;

		@ApiModelProperty(value = "商品库存更新数（正数代表扣减；负数代表回冲）", required = true)
		@NotNull
		private long count;
		
	}

}