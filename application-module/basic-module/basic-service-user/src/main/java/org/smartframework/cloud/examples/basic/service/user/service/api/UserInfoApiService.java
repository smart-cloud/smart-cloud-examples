package org.smartframework.cloud.examples.basic.service.user.service.api;

import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.user.UserInfoInsertReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.UserInfoBaseRespBody;
import org.smartframework.cloud.examples.basic.service.user.biz.api.UserInfoApiBiz;
import org.smartframework.cloud.examples.basic.service.user.config.UserParamValidateMessage;
import org.smartframework.cloud.examples.basic.service.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.starter.common.business.ReqContextHolder;
import org.smartframework.cloud.starter.common.business.exception.ParamValidateException;
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
	public UserInfoBaseRespBody query() {
		Long userId = ReqContextHolder.getUserId();
		return userInfoApiBiz.getUserInfoBaseMapper().selectRespById(userId);
	}

	/**
	 * 插入用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfoEntity insert(UserInfoInsertReqBody userInfo) {
		boolean existMobile = userInfoApiBiz.existByMobile(userInfo.getMobile());
		if (existMobile) {
			throw new ParamValidateException(UserParamValidateMessage.REGISTER_MOBILE_EXSITED);
		}

		return userInfoApiBiz.insert(userInfo);
	}

}