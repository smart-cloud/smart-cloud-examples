package org.smartframework.cloud.examples.support.gateway.util;

import org.smartframework.cloud.examples.support.gateway.enums.GatewayRedisKeyPrefix;

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

    /**
     * 获取重复提交key
     *
     * @param token
     * @return
     */
    public static String getRepeatSubmitCheckKey(String token) {
        return GatewayRedisKeyPrefix.REPEAT_SUBMIT_CHECK.getKey() + token;
    }

    /**
     * 获取签名相关的hashkey
     *
     * @return
     */
    public static String getSecurityHashKey() {
        return GatewayRedisKeyPrefix.SECURITY_HASH_KEY.getKey();
    }

    /**
     * 获取签名相关的key
     *
     * @param token
     * @return
     */
    public static String getSecurityKey(String token) {
        return token;
    }

    /**
     * 获取用户信息的hashkey
     *
     * @return
     */
    public static String getUserHashKey() {
        return GatewayRedisKeyPrefix.USER_HASH_KEY.getKey();
    }

    /**
     * 获取用户信息的key
     *
     * @param token
     * @return
     */
    public static String getUserKey(String token) {
        return token;
    }

    /**
     * 获取用户、token关联信息的hashkey
     *
     * @return
     */
    public static String getUserTokenRelationHashKey() {
        return GatewayRedisKeyPrefix.USER_TOKEN_RELATION_HASH_KEY.getKey();
    }

}