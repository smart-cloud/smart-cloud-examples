package org.smartframework.cloud.examples.basic.service.user.config;

import lombok.experimental.UtilityClass;

/**
 * 用户模块参数校验提示信息
 * 
 * @author liyulin
 * @date 2019年7月1日 上午11:50:51
 */
@UtilityClass
public class UserParamValidateMessage {

	/** 该手机号已存在，请换一个重新注册 */
	public static final String REGISTER_MOBILE_EXSITED = "该手机号已存在，请换一个重新注册";
	/** 该用户名已存在，请换一个重新注册 */
	public static final String REGISTER_USERNAME_EXSITED = "该用户名已存在，请换一个重新注册";
	
}