package org.smartframework.cloud.examples.basic.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 密码状态=={"1":"未设置","2":"已设置"}
 *
 * @author liyulin
 * @date 2019-07-13
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PwdStateEnum {

	/** 未设置 */
	NOT_SETTING((byte)1),
	/** 已设置 */
	DONE_SETTING((byte)2);

	private Byte value;
	
}