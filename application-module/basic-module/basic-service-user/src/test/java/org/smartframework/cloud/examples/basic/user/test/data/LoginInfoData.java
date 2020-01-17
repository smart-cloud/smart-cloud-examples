package org.smartframework.cloud.examples.basic.user.test.data;

import org.smartframework.cloud.examples.basic.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
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