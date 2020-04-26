package org.smartframework.cloud.examples.basic.user.service.api;

import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.biz.api.UserInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.config.UserParamValidateMessage;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.starter.core.business.SmartReqContext;
import org.smartframework.cloud.starter.core.business.exception.ParamValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoApiService {

	@Autowired
	private UserInfoApiBiz userInfoApiBiz;

	/**
	 * 根据id查询用户信息
	 * 
	 * @return
	 */
	public UserInfoBaseRespVO query() {
		Long userId = SmartReqContext.getUserId();
		return userInfoApiBiz.getUserInfoBaseMapper().selectRespById(userId);
	}

	/**
	 * 插入用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfoEntity insert(UserInfoInsertReqVO userInfo) {
		boolean existMobile = userInfoApiBiz.existByMobile(userInfo.getMobile());
		if (existMobile) {
			throw new ParamValidateException(UserParamValidateMessage.REGISTER_MOBILE_EXSITED);
		}

		return userInfoApiBiz.insert(userInfo);
	}

}