package org.smartframework.cloud.examples.support.gateway.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.starter.redis.RedisKeyUtil;
import org.smartframework.cloud.starter.redis.enums.RedisKeyPrefix;

/**
 * gateway redis key前缀
 *
 * @author liyulin
 * @date 2020-04-19
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GatewayRedisKeyPrefix {

    /**
     * gateway redis key前缀
     */
    GATEWAY_REDIS_KEY_PREFIX(RedisKeyUtil.buildKey(RedisKeyPrefix.DATA.getKey(), "gw", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * api meta
     */
    API_META(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "apimeta")),
    /**
     * 权限
     */
    AUTH(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "auth", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * auth user cache
     */
    REPEAT_SUBMIT_CHECK(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "rsc", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * 签名相关key
     */
    SECURITY_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "security:key")),
    /**
     * 用户信息key
     */
    USER_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "user:key")),
    /**
     * 用户、token信息key
     */
    USER_TOKEN_RELATION_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_REDIS_KEY_PREFIX.getKey(), "usertoken:key"));

    /**
     * redis key prefix
     */
    private String key;

}