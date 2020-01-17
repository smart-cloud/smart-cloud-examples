package org.smartframework.cloud.examples.basic.user.config;

import lombok.experimental.UtilityClass;

/**
 * 用户模块redis配置
 *
 * @author liyulin
 * @date 2019-06-29
 */
@UtilityClass
public class UserRedisConfig {

	/** 未登陆的token有效期（30分钟） */
	public static final long NON_LOGIN_TOKEN_EXPIRE_MILLIS = 1000 * 60 * 30;

	/** APP端已登陆的token有效期（30天） */
	public static final long APP_LOGINED_TOKEN_EXPIRE_MILLIS = 1000 * 60 * 60 * 30;

}