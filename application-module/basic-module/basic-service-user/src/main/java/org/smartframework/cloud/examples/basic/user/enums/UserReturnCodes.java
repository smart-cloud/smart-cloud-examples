package org.smartframework.cloud.examples.basic.user.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserReturnCodes implements IBaseReturnCodes {

	/** 账号不存在 */
	ACCOUNT_NOT_EXIST("100001"),
	/** 用户被禁用 */
	USER_UNENABLE("100002"),
	/** 用户已被删除 */
	USER_DELETED("100003"),
	/** 用户或密码错误 */
	USERNAME_OR_PASSWORD_ERROR("100004"),
	/** rsa密钥对生成出错 */
	GENERATE_RSAKEY_FAIL("100005"),
	/** 盐值生成失败 */
	GENERATE_SALT_FAIL("100006");

	/** 状态码 */
	private String code;

}