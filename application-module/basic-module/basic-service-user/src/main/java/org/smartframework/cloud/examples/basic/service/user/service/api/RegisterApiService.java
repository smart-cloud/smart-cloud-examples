package org.smartframework.cloud.examples.basic.service.user.service.api;

import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.LoginInfoInsertReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.register.RegisterUserReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.register.RegisterUserRespBody;
import org.smartframework.cloud.examples.basic.service.user.dto.login.LoginInfoInsertServiceDto;
import org.smartframework.cloud.examples.basic.service.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注册
 *
 * @author liyulin
 * @date 2019-06-29
 */
@Service
public class RegisterApiService {

	@Autowired
	private UserInfoApiService userInfoApiService;
	@Autowired
	private LoginInfoApiService loginInfoApiService;
	
	/**
	 * 注册
	 * 
	 * @param req
	 * @return
	 */
	@Transactional
	public Resp<RegisterUserRespBody> register(RegisterUserReqBody req){
		// 用户信息
		UserInfoEntity userInfoEntity = userInfoApiService.insert(req.getUserInfo());
		
		// 登陆信息
		LoginInfoInsertReqBody loginInfo = req.getLoginInfo();
		
		LoginInfoInsertServiceDto loginInfoInsertDto = LoginInfoInsertServiceDto.builder()
				.userId(userInfoEntity.getId())
				.username(loginInfo.getUsername())
				.password(loginInfo.getPassword())
				.pwdState(loginInfo.getPwdState())
				.build();
		loginInfoApiService.insert(loginInfoInsertDto);
		
		// 注册成功，则缓存
		Long userId = userInfoEntity.getId();
		loginInfoApiService.cacheLoginAfterLoginSuccess(userId);
		
		RegisterUserRespBody registerUserRespBody = new RegisterUserRespBody();
		registerUserRespBody.setUserId(userId);
		return RespUtil.success(registerUserRespBody);
	}
	
}