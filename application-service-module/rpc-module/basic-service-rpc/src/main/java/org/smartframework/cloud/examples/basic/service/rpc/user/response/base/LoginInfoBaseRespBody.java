package org.smartframework.cloud.examples.basic.service.rpc.user.response.base;

import java.util.Date;

import org.smartframework.cloud.common.pojo.dto.BaseEntityRespBody;

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
@ApiModel(description = "登陆信息响应信息")
public class LoginInfoBaseRespBody extends BaseEntityRespBody {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "16位盐值")
	private String salt;

	@ApiModelProperty(value = "最近成功登录时间")
	private Date lastLoginTime;

	@ApiModelProperty(value = "密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"}")
	private Byte pwdState;

	@ApiModelProperty(value = "用户状态=={\"1\":\"启用\",\"2\":\"禁用\"}")
	private Byte userState;

}