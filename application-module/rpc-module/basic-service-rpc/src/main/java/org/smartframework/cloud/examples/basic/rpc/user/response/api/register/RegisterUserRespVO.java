package org.smartframework.cloud.examples.basic.rpc.user.response.api.register;

import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ApiModel(description = "用户注册响应信息")
public class RegisterUserRespVO extends LoginRespVO {

	private static final long serialVersionUID = 1L;

	public RegisterUserRespVO(LoginRespVO loginRespVO) {
		super(loginRespVO.getUserId());
	}

}