package org.smartframework.cloud.example.support.gateway.enums;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.starter.redis.enums.RedisKeyPrefix;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * gateway redis key前缀
 *
 * @author liyulin
 * @date 2020-04-19
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GatewayRedisKeyPrefix {
	
	API_META(StringUtils.join(RedisKeyPrefix.DATA.getKey(), "apimeta",RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey()));
	
	private String key;
	
}