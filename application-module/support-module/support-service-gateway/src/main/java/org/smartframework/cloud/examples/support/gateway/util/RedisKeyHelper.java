package org.smartframework.cloud.examples.support.gateway.util;

import org.smartframework.cloud.examples.support.gateway.enums.GatewayRedisKeyPrefix;
import org.smartframework.cloud.starter.redis.RedisKeyUtil;

public class RedisKeyHelper {

	/**
	 * api meta redis key
	 * 
	 * @return
	 */
	public static String getApiMetaKey() {
		return GatewayRedisKeyPrefix.API_META.getKey();
	}

	/**
	 * api meta redis hash key
	 *
	 * @param urlMethod
	 * @return
	 */
	public static String getApiMetaHashKey(String urlMethod) {
		return urlMethod;
	}

}