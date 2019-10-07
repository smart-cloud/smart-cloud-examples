package org.smartframework.cloud.examples.basic.service.user.test.cases.integration.api;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.CacheDesKeyReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.LoginReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.login.LoginRespBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.UserInfoBaseRespBody;
import org.smartframework.cloud.examples.basic.service.user.config.UserRedisConfig;
import org.smartframework.cloud.examples.basic.service.user.test.data.LoginInfoData;
import org.smartframework.cloud.starter.common.business.LoginCache;
import org.smartframework.cloud.starter.common.business.security.LoginRedisConfig;
import org.smartframework.cloud.starter.common.business.security.util.ReqHttpHeadersUtil;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.smartframework.cloud.utility.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

@Rollback
@Transactional
public class LoginInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private RedisComponent redisComponent;
	@Autowired
	private LoginInfoData loginInfoData;

	@Before
	public void after() {
		redisComponent.delete(LoginRedisConfig.getTokenRedisKey("*"));
		redisComponent.delete(LoginRedisConfig.getUserIdRedisKey(null)+"*");
	}
	
	@Test
	public void testGetRsaKey() throws Exception {
		// 构造请求参数
		Resp<UserInfoBaseRespBody> result = super.postWithNoHeaders("/api/open/user/loginInfo/getRsaKey", null,
				new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}

	@Test
	public void testCacheDesKey() throws Exception {
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);

		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);

		// 构造请求参数
		CacheDesKeyReqBody reqBody = new CacheDesKeyReqBody();
		reqBody.setKey(RandomUtil.generateRandom(false, 10));

		Resp<BaseDto> result = super.postWithHeaders("/api/sign/user/loginInfo/cacheDesKey", reqBody, token,
				new TypeReference<Resp<BaseDto>>() {
				});

//		redisComponent.delete(tokenRedisKey);

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testLogin() throws Exception {
		// insert login info
		String username = "zhangsan";
		String password = "123456";
		loginInfoData.insert(username, password);

		// setting cache
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);

		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);

		// 构造请求参数
		LoginReqBody reqBody = new LoginReqBody();
		reqBody.setUsername(username);
		reqBody.setPassword(password);

		Resp<LoginRespBody> result = super.postWithHeaders("/api/sign/user/loginInfo/login", reqBody, token,
				new TypeReference<Resp<LoginRespBody>>() {
				});

//		redisComponent.delete(tokenRedisKey);

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}