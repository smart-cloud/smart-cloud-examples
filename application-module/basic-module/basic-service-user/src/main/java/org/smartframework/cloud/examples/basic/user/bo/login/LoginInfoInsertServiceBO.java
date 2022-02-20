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
package org.smartframework.cloud.examples.basic.user.bo.login;

import io.github.smart.cloud.common.pojo.Base;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 插入登陆信息
 * 
 * @author collin
 * @date 2019-07-01
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertServiceBO extends Base {

	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** "密码状态=={\"1\":\"未设置\",\"2\":\"已设置\"} */
	private Byte pwdState;

	/** 用户id */
	private Long userId;

}