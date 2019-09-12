package org.smartframework.cloud.examples.basic.service.rpc.user.request.api.register;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.LoginInfoInsertReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.user.UserInfoInsertReqBody;

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
@ApiModel(description = "用户注册请求参数")
public class RegisterUserReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="用户信息", required=true)
	@NotNull
	@Valid
	private UserInfoInsertReqBody userInfo;

	@ApiModelProperty(value="登陆信息", required=true)
	@NotNull
	@Valid
	private LoginInfoInsertReqBody loginInfo;

}