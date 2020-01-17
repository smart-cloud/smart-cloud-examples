package org.smartframework.cloud.examples.basic.user.enums;

import org.smartframework.cloud.common.pojo.enums.IBaseReturnCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserReturnCodeEnum implements IBaseReturnCode {

	/** 账号不存在 */
	ACCOUNT_NOT_EXIST("203101", "账号不存在"),
	/** 用户被禁用 */
	USER_UNENABLE("203102", "用户被禁用"),
	/** 用户已被删除 */
	USER_DELETED("203103", "用户已被删除"),
	/** 用户或密码错误 */
	USERNAME_OR_PASSWORD_ERROR("203401", "用户名或密码错误"),
	/** rsa密钥对生成出错 */
	GENERATE_RSAKEY_FAIL("203501", "rsa密钥对生成出错"),
	/** 盐值生成失败 */
	GENERATE_SALT_FAIL("203502", "盐值生成失败");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String message;

}