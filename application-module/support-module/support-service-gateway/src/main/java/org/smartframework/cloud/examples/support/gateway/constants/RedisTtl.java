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
package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * redis 缓存有效数
 *
 * @author collin
 * @date 2020-09-10
 */
public interface RedisTtl {

    /**
     * 未登录用户security key有效期
     */
    long SECURITY_KEY_NON_LOGIN = 5 * 60 * 1000L;

    /**
     * 登录成功后security key有效期
     */
    long SECURITY_KEY_LOGIN_SUCCESS = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 登录成功后用户信息有效期（单位：毫秒）
     */
    long USER_LOGIN_SUCCESS = SECURITY_KEY_LOGIN_SUCCESS;

    /**
     * 用户缓存刷新阈值
     */
    long USER_CACHE_REFRESH_THRESHOLD = USER_LOGIN_SUCCESS / 4;

}