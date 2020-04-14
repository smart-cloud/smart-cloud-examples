package org.smartframework.cloud.example.support.gateway.util;

import org.smartframework.cloud.example.support.gateway.enums.GatewayRedisKeyPrefix;

public class RedisKeyHelper {

	/**
	 * api meta redis key
	 * 
	 * @param urlMethod
	 * @return
	 */
	public static String getApiMetaKey(String urlMethod) {
		return GatewayRedisKeyPrefix.API_META.getKey() + urlMethod;
	}

}