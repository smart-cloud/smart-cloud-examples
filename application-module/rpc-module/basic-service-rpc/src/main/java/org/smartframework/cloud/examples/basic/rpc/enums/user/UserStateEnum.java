package org.smartframework.cloud.examples.basic.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态=={"1":"启用","2":"禁用"}
 *
 * @author liyulin
 * @date 2019-06-29
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserStateEnum {
	
	/** 启用 */
	ENABLE((byte)1),
	/** 禁用 */
	UNENABLE((byte)2);

	private Byte value;

}