/*
 * Copyright © 2019 collin =1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 =the "License");
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
package org.smartframework.cloud.examples.basic.user.constants;

public interface UserReturnCodes {

    /**
     * 账号不存在
     */
    String ACCOUNT_NOT_EXIST = "100001";
    /**
     * 用户被禁用
     */
    String USER_UNENABLE = "100002";
    /**
     * 用户已被删除
     */
    String USER_DELETED = "100003";
    /**
     * 用户或密码错误
     */
    String USERNAME_OR_PASSWORD_ERROR = "100004";
    /**
     * 该手机号已存在，请换一个重新注册
     */
    String REGISTER_MOBILE_EXSITED = "100005";
    /**
     * 该用户名已存在，请换一个重新注册
     */
    String REGISTER_USERNAME_EXSITED = "100006";
    /**
     * rsa密钥对生成出错
     */
    String GENERATE_RSAKEY_FAIL = "100007";
    /**
     * 盐值生成失败
     */
    String GENERATE_SALT_FAIL = "100008";

}