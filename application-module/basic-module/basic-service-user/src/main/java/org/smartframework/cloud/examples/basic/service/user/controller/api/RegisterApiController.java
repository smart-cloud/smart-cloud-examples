package org.smartframework.cloud.examples.basic.service.user.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.basic.service.rpc.user.request.api.register.RegisterUserReqBody;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.api.register.RegisterUserRespBody;
import org.smartframework.cloud.examples.basic.service.user.service.api.RegisterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("api/sign/user/register")
@Api(tags = "注册api接口")
public class RegisterApiController {

	@Autowired
	private RegisterApiService registerApiService;

	@PostMapping
	@ApiOperation("注册")
	public Resp<RegisterUserRespBody> register(@RequestBody @Valid RegisterUserReqBody req) {
		return registerApiService.register(req);
	}

}