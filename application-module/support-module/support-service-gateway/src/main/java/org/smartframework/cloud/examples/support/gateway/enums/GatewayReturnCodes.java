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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GatewayReturnCodes implements IBaseReturnCodes {

    /**
     * 获取api meta失败
     */
    FETCH_APIMETA_FAIL("400001"),
    /**
     * rsa密钥对生成出错
     */
    GENERATE_RSAKEY_FAIL("400002"),
    /**
     * 登录前token失效
     */
    TOKEN_EXPIRED_BEFORE_LOGIN("400003"),
    /**
     * 登录成功后token失效
     */
    TOKEN_EXPIRED_LOGIN_SUCCESS("400004"),
    /**
     * 请求参数中token缺失
     */
    TOKEN_MISSING("400005"),
    /**
     * 请求签名缺失
     */
    REQUEST_SIGN_MISSING("400006"),
    /**
     * 请求参数签名校验失败
     */
    REQUEST_SIGN_CHECK_FAIL("400007"),
    /**
     * 当前用户暂未登陆，获取userId失败
     */
    GET_USERID_FAIL("400008"),
    /**
     * 未获取到登陆缓存信息
     */
    LOGIN_CACHE_MISSING("400009"),
    /**
     * 请求时间戳为空
     */
    REQUEST_TIMESTAMP_MISSING("400010"),
    /**
     * 请求时间戳格式错误
     */
    REQUEST_TIMESTAMP_FORMAT_INVALID("400011"),
    /**
     * 请求时间戳非法
     */
    REQUEST_TIMESTAMP_ILLEGAL("400012"),
    /**
     * security key过期
     */
    SECURITY_KEY_EXPIRED("400013"),
    /**
     * AES key获取失败
     */
    AES_KEY_NOT_FOUND("400014");

    /**
     * 状态码
     */
    private String code;

}