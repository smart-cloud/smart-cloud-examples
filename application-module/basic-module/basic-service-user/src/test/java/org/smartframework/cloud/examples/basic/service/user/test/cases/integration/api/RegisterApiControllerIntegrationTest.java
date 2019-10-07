package org.smartframework.cloud.examples.basic.service.user.test.cases.integration.api;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.basic.service.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.service.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.service.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.LoginInfoInsertReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.register.RegisterUserReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.user.UserInfoInsertReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.register.RegisterUserRespBody;
import org.smartframework.cloud.examples.basic.service.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.examples.basic.service.user.service.api.RegisterApiService;
import org.smartframework.cloud.starter.common.business.security.LoginRedisConfig;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

@Rollback
@Transactional
public class RegisterApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private RedisComponent redisComponent;

	@Before
	public void after() {
		redisComponent.delete(LoginRedisConfig.getTokenRedisKey("*"));
		redisComponent.delete(LoginRedisConfig.getUserIdRedisKey(null)+"*");
	}
	
	@Test
	public void testRegister() throws Exception {
		// mock
		LoginInfoApiService loginInfoApiService = Mockito.mock(LoginInfoApiService.class);
		RegisterApiService registerApiService = applicationContext.getBean(RegisterApiService.class);
		setMockAttribute(registerApiService, loginInfoApiService);
		Mockito.doNothing().when(loginInfoApiService).cacheLoginAfterLoginSuccess(Mockito.any());

		// 构造请求参数
		UserInfoInsertReqBody userInfo = new UserInfoInsertReqBody();
		userInfo.setMobile("18720912981");
		userInfo.setChannel(ChannelEnum.APP.getValue());
		userInfo.setSex(SexEnum.FEMALE.getValue());

		LoginInfoInsertReqBody loginInfo = new LoginInfoInsertReqBody();
		loginInfo.setUsername("zhangsan");
		loginInfo.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
		loginInfo.setPassword("123456");

		RegisterUserReqBody registerUserReqBody = new RegisterUserReqBody();
		registerUserReqBody.setUserInfo(userInfo);
		registerUserReqBody.setLoginInfo(loginInfo);

		Resp<RegisterUserRespBody> result = super.postWithNoHeaders("/api/sign/user/register", registerUserReqBody,
				new TypeReference<Resp<RegisterUserRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}

}