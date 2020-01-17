package org.smartframework.cloud.examples.basic.user.bo.login;

import org.smartframework.cloud.common.pojo.Base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 插入登陆信息
 * 
 * @author liyulin
 * @date 2019-07-01
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertServiceBO extends Base {

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