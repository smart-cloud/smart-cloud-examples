package org.smartframework.cloud.examples.basic.service.rpc.user.response.api.login;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

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
@ApiModel(description = "获取rsa key响应信息")
public class GetRsaKeyRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("访问token")
	private String token;

	@ApiModelProperty("签名的modulus")
	private String signModules;

	@ApiModelProperty("签名的key")
	private String signKey;

	@ApiModelProperty("校验签名的modulus")
	private String checkSignModulus;

	@ApiModelProperty("校验签名的key")
	private String checkSignKey;

}