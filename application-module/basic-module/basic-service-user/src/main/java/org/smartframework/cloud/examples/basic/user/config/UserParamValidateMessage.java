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
package org.smartframework.cloud.examples.basic.user.config;

import lombok.experimental.UtilityClass;

/**
 * 用户模块参数校验提示信息
 * 
 * @author collin
 * @date 2019-07-01
 */
@UtilityClass
public class UserParamValidateMessage {

	/** 该手机号已存在，请换一个重新注册 */
	public static final String REGISTER_MOBILE_EXSITED = "user.register.mobile.existed";
	/** 该用户名已存在，请换一个重新注册 */
	public static final String REGISTER_USERNAME_EXSITED = "user.register.username.existed";
	
}