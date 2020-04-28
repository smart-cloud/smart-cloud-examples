package org.smartframework.cloud.example.support.gateway.controller.rpc;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.example.support.gateway.service.rpc.GatewayAuthRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.GatewayAuthRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUpdateReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUploadReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class GatewayAuthRpcController implements GatewayAuthRpc {

	@Autowired
	private GatewayAuthRpcService gatewayAuthRpcService;

	@Override
	public RespVO<Base> upload(@RequestBody @Valid GatewayAuthUploadReqVO req) {
		return gatewayAuthRpcService.upload(req);
	}

	@Override
	public RespVO<Base> update(@RequestBody @Valid GatewayAuthUpdateReqVO req) {
		return gatewayAuthRpcService.update(req);
	}

}