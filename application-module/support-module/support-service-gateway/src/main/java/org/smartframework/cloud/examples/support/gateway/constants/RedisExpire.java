package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * redis 缓存有效数
 *
 * @author liyulin
 * @date 2020-09-10
 */
public interface RedisExpire {

    /**
     * 未登录用户security key有效期
     */
    int SECURITY_KEY_EXPIRE_MILLIS_NON_LOGIN = 5 * 60;

    /**
     * 登录成功后security key有效期
     */
    int SECURITY_KEY_EXPIRE_MILLIS_LOGIN_SUCCESS = 7 * 24 * 60 * 60;

    /**
     * 登录成功后用户信息有效期
     */
    int USER_EXPIRE_MILLIS_LOGIN_SUCCESS = SECURITY_KEY_EXPIRE_MILLIS_LOGIN_SUCCESS;

}