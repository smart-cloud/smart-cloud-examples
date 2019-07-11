package org.smartframework.cloud.examples.basic.service.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所在平台=={"1":"app","2":"web后台","3":"微信"}
 *
 * @author liyulin
 * @date 2019年6月29日上午11:49:59
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChannelEnum {

	/** app */
	APP((byte) 1),
	/** web后台 */
	WEB((byte) 2),
	/** 微信 */
	WEIXIN((byte) 3);

	private Byte value;

}