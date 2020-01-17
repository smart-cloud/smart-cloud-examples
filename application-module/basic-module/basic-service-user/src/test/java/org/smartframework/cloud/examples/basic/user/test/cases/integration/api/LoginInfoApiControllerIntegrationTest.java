package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.CacheDesKeyReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.config.UserRedisConfig;
import org.smartframework.cloud.examples.basic.user.test.data.LoginInfoData;
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

	@Test
	public void testGetRsaKey() throws Exception {
		// 构造请求参数
		RespVO<UserInfoBaseRespVO> result = super.postWithNoHeaders("/api/open/user/loginInfo/getRsaKey", null,
				new TypeReference<RespVO<UserInfoBaseRespVO>>() {
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
		CacheDesKeyReqVO reqVO = new CacheDesKeyReqVO();
		reqVO.setKey(RandomUtil.generateRandom(false, 10));

		RespVO<Base> result = super.postWithHeaders("/api/sign/user/loginInfo/cacheDesKey", reqVO, token,
				new TypeReference<RespVO<Base>>() {
				});

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
		LoginReqVO reqVO = new LoginReqVO();
		reqVO.setUsername(username);
		reqVO.setPassword(password);

		RespVO<LoginRespVO> result = super.postWithHeaders("/api/sign/user/loginInfo/login", reqVO, token,
				new TypeReference<RespVO<LoginRespVO>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}