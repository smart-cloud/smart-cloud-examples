package org.smartframework.cloud.examples.basic.service.user.dto.login;

import org.smartframework.cloud.common.pojo.dto.BaseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 插入登陆信息
 * 
 * @author liyulin
 * @date 2019年7月1日 上午11:06:40
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertServiceDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** "密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"} */
	private Byte pwdState;

	/** 用户id */
	private Long userId;

}