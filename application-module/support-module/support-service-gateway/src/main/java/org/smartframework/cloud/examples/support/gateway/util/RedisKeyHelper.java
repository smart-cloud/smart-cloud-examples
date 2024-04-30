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
package org.smartframework.cloud.examples.support.gateway.util;

import io.github.smart.cloud.starter.redis.RedisKeyUtil;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayRedisKeyPrefix;

public final class RedisKeyHelper {

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

    /**
     * 获取重复提交key
     *
     * @param token
     * @param paramMd5
     * @return
     */
    public static String getRepeatSubmitCheckKey(String token, String paramMd5) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.REPEAT_SUBMIT_CHECK.getKey(), token, paramMd5);
    }

    /**
     * 获取签名相关的key
     *
     * @param token
     * @return
     */
    public static String getSecurityKey(String token) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.SECURITY_HASH_KEY.getKey(), token);
    }

    /**
     * 获取用户信息的key
     *
     * @param token
     * @return
     */
    public static String getUserKey(String token) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.USER_KEY.getKey(), token);
    }

    /**
     * 获取权限信息的key
     *
     * @param uid
     * @return
     */
    public static String getAuthKey(Long uid) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.AUTH_HASH_KEY.getKey(), String.valueOf(uid));
    }

    /**
     * 获取用户、token关联信息的key
     *
     * @param userId
     * @return
     */
    public static String getUserTokenRelationKey(Long userId) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.USER_TOKEN_RELATION_HASH_KEY.getKey(), String.valueOf(userId));
    }

    /**
     * 用户权限信息二级缓存hashkey
     *
     * @param token
     * @return
     */
    public static String getUserAuthSecondaryCacheKey(String token) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.USER_AUTH_SECONDARY_CACHE_HASH_KEY.getKey(), token);
    }

    /**
     * 用户权限信息二级缓存key
     *
     * @param urlMethod
     * @return
     */
    public static String getUserAuthSecondaryCacheHashKey(String urlMethod) {
        return urlMethod;
    }

    /**
     * 获取api mate更新锁key
     *
     * @param serviceName
     * @return
     */
    public static String getApiMetaLockKey(String serviceName) {
        return RedisKeyUtil.buildKey(GatewayRedisKeyPrefix.API_META_UPDATE_LOCK_KEY_PREFIX.getKey(), serviceName);
    }

}