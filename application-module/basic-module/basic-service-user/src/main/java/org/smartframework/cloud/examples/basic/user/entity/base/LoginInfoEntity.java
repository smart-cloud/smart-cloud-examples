package org.smartframework.cloud.examples.basic.user.entity.base;

import org.smartframework.cloud.mask.MaskRule;

import org.smartframework.cloud.mask.MaskLog;

import java.util.Date;

import org.smartframework.cloud.mask.EnableMask;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 登录信息
 *
 * @author liyulin
 * @date 2019-11-23
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_login_info")
@EnableMask
public class LoginInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Column(name = "t_user_id")     
	private Long userId;
	
    /** 用户名 */
    @Column(name = "f_username")     
	private String username;
	
    /** 密码（md5加盐处理） */
    @MaskLog(MaskRule.PASSWROD)
    @Column(name = "f_password")     
	private String password;
	
    /** 16位盐值 */
    @MaskLog(MaskRule.DEFAULT)
    @Column(name = "f_salt")     
	private String salt;
	
    /** 最近成功登录时间 */
    @Column(name = "f_last_login_time")     
	private Date lastLoginTime;
	
    /** 密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"} */
    @Column(name = "f_pwd_state")     
	private Byte pwdState;
	
    /** 用户状态=={\"1\":\"启用\",\"2\":\"禁用\"} */
    @Column(name = "f_user_state")     
	private Byte userState;
	
	/** 表字段名 */
	public enum Columns {
		userId,
	    /** 用户名 */
		username,
	    /** 密码（md5加盐处理） */
		password,
	    /** 16位盐值 */
		salt,
	    /** 最近成功登录时间 */
		lastLoginTime,
	    /** 密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"} */
		pwdState,
	    /** 用户状态=={\"1\":\"启用\",\"2\":\"禁用\"} */
		userState;
	}

}