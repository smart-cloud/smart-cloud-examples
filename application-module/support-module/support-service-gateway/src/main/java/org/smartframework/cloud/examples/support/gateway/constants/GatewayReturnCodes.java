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
 * 网关服务异常码
 *
 * @author collin
 * @date 2024-03-27
 */
public interface GatewayReturnCodes {

    /**
     * 获取api meta失败
     */
    String FETCH_APIMETA_FAIL = "400001";
    /**
     * rsa密钥对生成出错
     */
    String GENERATE_RSAKEY_FAIL = "400002";
    /**
     * 登录前token失效
     */
    String TOKEN_EXPIRED_BEFORE_LOGIN = "400003";
    /**
     * 登录成功后token失效
     */
    String TOKEN_EXPIRED_LOGIN_SUCCESS = "400004";
    /**
     * 请求参数中token缺失
     */
    String TOKEN_MISSING = "400005";
    /**
     * 请求签名缺失
     */
    String REQUEST_SIGN_MISSING = "400006";
    /**
     * 请求参数签名校验失败
     */
    String REQUEST_SIGN_CHECK_FAIL = "400007";
    /**
     * 生成签名失败
     */
    String GENERATE_SIGN_FAIL = "400008";
    /**
     * 生成签名key失败
     */
    String GENERATE_SIGN_KEY_FAIL = "400009";
    /**
     * 请求时间戳不能为空
     */
    String REQUEST_TIMESTAMP_MISSING = "400010";
    /**
     * 请求时间戳格式错误
     */
    String REQUEST_TIMESTAMP_FORMAT_INVALID = "400011";
    /**
     * 请求时间戳非法
     */
    String REQUEST_TIMESTAMP_ILLEGAL = "400012";
    /**
     * security key过期
     */
    String SECURITY_KEY_EXPIRED = "400013";
    /**
     * 命中黑名单列表，禁止访问
     */
    String BLACK_LIST_FORBIDDEN_ACCSS = "400015";
    /**
     * 不在白名单中，禁止访问
     */
    String NOT_IN_WHITE_LIST = "400016";
    /**
     * 不支持数据安全
     */
    String NOT_SUPPORT_DATA_SECURITY = "400017";
    /**
     * 请求nonce缺失
     */
    String REQUEST_NONCE_MISSING = "400018";

}