package org.smartframework.cloud.examples.basic.user.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.RegisterApiService;
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
	public RespVO<RegisterUserRespVO> register(@RequestBody @Valid RegisterUserReqVO req) {
		return registerApiService.register(req);
	}

}