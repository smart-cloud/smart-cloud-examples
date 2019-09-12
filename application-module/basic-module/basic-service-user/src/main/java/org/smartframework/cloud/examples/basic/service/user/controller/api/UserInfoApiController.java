package org.smartframework.cloud.examples.basic.service.user.controller.api;

import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.UserInfoBaseRespBody;
import org.smartframework.cloud.examples.basic.service.user.service.api.UserInfoApiService;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("api/identity/user/userInfo")
@Api(tags = "用户api接口")
public class UserInfoApiController {

	@Autowired
	private UserInfoApiService userInfoApIService;

	@GetMapping("query")
	@ApiOperation("查询当前用户信息")
	public Resp<UserInfoBaseRespBody> query() {
		return RespUtil.success(userInfoApIService.query());
	}

}