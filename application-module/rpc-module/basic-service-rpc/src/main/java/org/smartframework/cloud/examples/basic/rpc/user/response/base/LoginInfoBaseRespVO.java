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
package org.smartframework.cloud.examples.basic.rpc.user.response.base;

import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.MaskLog;
import java.util.Date;
import org.smartframework.cloud.common.pojo.BaseEntityResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 登录信息
 *
 * @author collin
 * @date 2021-12-12
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
    /** 用户名 */
	private String username;
	
    /** 密码（md5加盐处理） */
    @MaskLog(MaskRule.PASSWROD)
	private String password;
	
    /** 16位盐值 */
    @MaskLog(MaskRule.DEFAULT)
	private String salt;
	
    /** 最近成功登录时间 */
	private Date lastLoginTime;
	
    /** 密码状态=={"1":"未设置","2":"已设置"} */
	private Byte pwdState;
	
    /** 用户状态=={"1":"启用","2":"禁用"} */
	private Byte userState;
	
}