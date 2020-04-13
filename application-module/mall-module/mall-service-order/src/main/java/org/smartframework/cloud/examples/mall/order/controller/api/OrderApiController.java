package org.smartframework.cloud.examples.mall.order.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("order/api/order")
@Validated
@Api(tags = "订单api相关接口")
public class OrderApiController {

	@Autowired
	private OrderApiService orderApiService;

	@ApiOperation("创建订单")
	@PostMapping("create")
	@SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true, auth = true, repeatSubmitCheck = true)
	public RespVO<CreateOrderRespVO> create(@RequestBody @Valid CreateOrderReqVO req) {
		return orderApiService.create(req);
	}

}