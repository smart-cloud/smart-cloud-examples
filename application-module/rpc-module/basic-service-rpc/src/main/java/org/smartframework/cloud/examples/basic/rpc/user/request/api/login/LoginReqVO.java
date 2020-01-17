package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

import javax.validation.constraints.NotBlank;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.mask.EnableMask;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;

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
@ApiModel(description = "登陆请求参数")
@EnableMask
public class LoginReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank
	private String username;

	@ApiModelProperty(value = "密码", required = true)
	@NotBlank
	@MaskLog(MaskRule.PASSWROD)
	private String password;

}