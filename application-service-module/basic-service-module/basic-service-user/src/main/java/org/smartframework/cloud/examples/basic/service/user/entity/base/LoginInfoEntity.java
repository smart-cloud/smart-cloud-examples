package org.smartframework.cloud.examples.basic.service.user.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 登陆信息
 *
 * @author liyulin
 * @date 2019-06-28
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_login_info")
public class LoginInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 用户id */
	@Column(name = "t_user_id")
	private Long userId;

	/** 用户名 */
	@Column(name = "f_username")
	private String username;

	/** 密码（md5加盐处理） */
	@Column(name = "f_password")
	private String password;

	/** 16位盐值 */
	@Column(name = "f_salt")
	private String salt;

	/** 最近成功登录时间 */
	@Column(name = "f_last_login_time")
	private Date lastLoginTime;

	/** 密码状态=={"1":"未设置","2":"已设置"} */
	@Column(name = "f_pwd_state")
	private Byte pwdState;

	/** 用户状态=={"1":"启用","2":"禁用"} */
	@Column(name = "f_user_state")
	private Byte userState;

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum Columns {
		/** 用户id */
		USERID("userId"),
		/** 用户名 */
		USERNAME("username"),
		/** 密码 */
		PASSWORD("password"),
		/** 16位盐值 */
		SALT("salt"),
		/** 最近成功登录时间 */
		LAST_LOGIN_TIME("lastLoginTime"),
		/** 密码状态 */
		PWD_STATE("pwdState"),
		/** 用户状态 */
		USER_STATE("userState");

		private String property;
	}

}