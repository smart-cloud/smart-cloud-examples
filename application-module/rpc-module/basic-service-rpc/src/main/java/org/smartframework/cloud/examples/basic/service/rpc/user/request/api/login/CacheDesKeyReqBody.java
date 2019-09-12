package org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

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
@ApiModel(description = "缓存aes key请求参数")
public class CacheDesKeyReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "aes key", required = true)
	@Size(min = 8, max = 16)
	@NotBlank
	private String key;

}