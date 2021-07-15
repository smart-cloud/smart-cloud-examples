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
     * gateway data redis key前缀
     */
    GATEWAY_DATA_REDIS_KEY_PREFIX(RedisKeyUtil.buildKey(RedisKeyPrefix.DATA.getKey(), "gw", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * gateway lock redis key前缀
     */
    GATEWAY_LOCK_REDIS_KEY_PREFIX(RedisKeyUtil.buildKey(RedisKeyPrefix.LOCK.getKey(), "gw", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * api meta
     */
    API_META(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "apimeta")),
    /**
     * auth user cache
     */
    REPEAT_SUBMIT_CHECK(RedisKeyUtil.buildKey(GATEWAY_LOCK_REDIS_KEY_PREFIX.getKey(), "rsc", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * 签名相关key
     */
    SECURITY_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "security")),
    /**
     * 用户信息key
     */
    USER_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "user")),
    /**
     * 用户、token信息key
     */
    USER_TOKEN_RELATION_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "usertoken")),
    /**
     * 权限信息key
     */
    AUTH_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "auth"));

    /**
     * redis key prefix
     */
    private String key;

}