package org.smartframework.cloud.examples.basic.rpc.user.request.api.register;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.mask.EnableMask;

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
@EnableMask
public class RegisterUserReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="用户信息", required=true)
	@NotNull
	@Valid
	private UserInfoInsertReqVO userInfo;

	@ApiModelProperty(value="登陆信息", required=true)
	@NotNull
	@Valid
	private LoginInfoInsertReqVO loginInfo;

}