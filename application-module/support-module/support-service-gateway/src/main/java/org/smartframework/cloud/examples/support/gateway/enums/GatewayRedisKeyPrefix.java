/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.support.gateway.enums;

import io.github.smart.cloud.starter.redis.RedisKeyUtil;
import io.github.smart.cloud.starter.redis.enums.RedisKeyPrefix;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * gateway redis key前缀
 *
 * @author collin
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
     * gateway data redis key前缀
     */
    GATEWAY_CACHE_REDIS_KEY_PREFIX(RedisKeyUtil.buildKey(RedisKeyPrefix.CACHE.getKey(), "gw", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * gateway lock redis key前缀
     */
    GATEWAY_LOCK_REDIS_KEY_PREFIX(RedisKeyUtil.buildKey(RedisKeyPrefix.LOCK.getKey(), "gw", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * api meta
     */
    API_META(RedisKeyUtil.buildKey(GATEWAY_DATA_REDIS_KEY_PREFIX.getKey(), "apimeta")),
    /**
     * 重复提交
     */
    REPEAT_SUBMIT_CHECK(RedisKeyUtil.buildKey(GATEWAY_LOCK_REDIS_KEY_PREFIX.getKey(), "rsc", RedisKeyPrefix.REDIS_KEY_SEPARATOR.getKey())),
    /**
     * 签名相关key
     */
    SECURITY_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_CACHE_REDIS_KEY_PREFIX.getKey(), "security")),
    /**
     * 用户信息key
     */
    USER_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_CACHE_REDIS_KEY_PREFIX.getKey(), "user")),
    /**
     * 用户、token信息key
     */
    USER_TOKEN_RELATION_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_CACHE_REDIS_KEY_PREFIX.getKey(), "usertoken")),
    /**
     * 权限信息key
     */
    AUTH_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_CACHE_REDIS_KEY_PREFIX.getKey(), "auth")),
    /**
     * 权限信息二级缓存
     */
    USER_AUTH_SECONDARY_CACHE_HASH_KEY(RedisKeyUtil.buildKey(GATEWAY_CACHE_REDIS_KEY_PREFIX.getKey(), "auth:secondary:"));

    /**
     * redis key prefix
     */
    private String key;

}