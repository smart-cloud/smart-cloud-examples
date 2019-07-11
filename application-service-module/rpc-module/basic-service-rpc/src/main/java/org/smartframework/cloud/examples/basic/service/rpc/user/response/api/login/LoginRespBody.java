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
@ApiModel(description = "登陆响应信息")
public class LoginRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
	private Long userId;

}