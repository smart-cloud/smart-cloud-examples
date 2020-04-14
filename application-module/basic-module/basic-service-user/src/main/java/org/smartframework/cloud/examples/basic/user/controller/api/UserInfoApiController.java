package org.smartframework.cloud.examples.basic.user.controller.api;

import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.UserInfoApiService;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("user/api/userInfo")
@Api(tags = "用户api接口")
public class UserInfoApiController {

	@Autowired
	private UserInfoApiService userInfoApIService;

	@GetMapping("query")
	@ApiOperation("查询当前用户信息")
	@SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
	public RespVO<UserInfoBaseRespVO> query() {
		return RespUtil.success(userInfoApIService.query());
	}

}