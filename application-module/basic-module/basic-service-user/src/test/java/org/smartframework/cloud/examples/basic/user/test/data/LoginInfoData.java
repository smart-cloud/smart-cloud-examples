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
package org.smartframework.cloud.examples.basic.user.test.data;

import org.smartframework.cloud.examples.basic.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.entity.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginInfoData {

	@Autowired
	private LoginInfoApiService loginInfoApiService;
	
	public LoginInfoEntity insert(String username, String password) {
		LoginInfoInsertServiceBO dto = new LoginInfoInsertServiceBO();
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setUserId(1000L);
		dto.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
		return loginInfoApiService.insert(dto);
	}
	
}