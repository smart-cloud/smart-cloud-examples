package org.smartframework.cloud.examples.mall.service.order.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.order.service.api.OrderApiService;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.api.CreateOrderRespBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/identity/order/order")
@Validated
@Api(tags = "订单api相关接口")
public class OrderApiController {

	@Autowired
	private OrderApiService orderApiService;

	@ApiOperation("创建订单")
	@PostMapping("create")
	public Resp<CreateOrderRespBody> create(@RequestBody @Valid CreateOrderReqBody req) {
		return orderApiService.create(req);
	}

}