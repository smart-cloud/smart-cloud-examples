package org.smartframework.cloud.examples.basic.service.user.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.CacheDesKeyReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.login.LoginReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.login.GetRsaKeyRespBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.login.LoginRespBody;
import org.smartframework.cloud.examples.basic.service.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@Api(tags = "登陆api接口")
public class LoginInfoApiController {

	@Autowired
	private LoginInfoApiService loginInfoApiService;

	@PostMapping("api/open/user/loginInfo/getRsaKey")
	@ApiOperation("获取rsa key")
	public Resp<GetRsaKeyRespBody> getRsaKey() {
		return RespUtil.success(loginInfoApiService.generateRsaKey());
	}

	@PostMapping("api/sign/user/loginInfo/cacheDesKey")
	@ApiOperation("缓存aes key")
	public Resp<BaseDto> cacheDesKey(@RequestBody @Valid CacheDesKeyReqBody req) {
		loginInfoApiService.cacheDesKey(req);
		return RespUtil.success();
	}

	@PostMapping("api/sign/user/loginInfo/login")
	@ApiOperation("登陆")
	public Resp<LoginRespBody> login(@RequestBody @Valid LoginReqBody req) {
		return loginInfoApiService.login(req);
	}

}