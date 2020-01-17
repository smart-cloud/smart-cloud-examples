package org.smartframework.cloud.examples.basic.user.config;

import lombok.experimental.UtilityClass;

/**
 * 用户模块参数校验提示信息
 * 
 * @author liyulin
 * @date 2019-07-01
 */
@UtilityClass
public class UserParamValidateMessage {

	/** 该手机号已存在，请换一个重新注册 */
	public static final String REGISTER_MOBILE_EXSITED = "该手机号已存在，请换一个重新注册";
	/** 该用户名已存在，请换一个重新注册 */
	public static final String REGISTER_USERNAME_EXSITED = "该用户名已存在，请换一个重新注册";
	
}