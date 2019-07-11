package org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(description = "添加登陆信息请求参数")
public class LoginInfoInsertReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户名", required = true)
	@Size(min = 6, max = 20)
	@NotBlank
	private String username;

	@ApiModelProperty(value = "密码", required = true)
	@Size(min = 6, max = 45)
	@NotBlank
	private String password;
	
	@ApiModelProperty(value = "密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"}", required = true)
	@NotNull
	private Byte pwdState;

}