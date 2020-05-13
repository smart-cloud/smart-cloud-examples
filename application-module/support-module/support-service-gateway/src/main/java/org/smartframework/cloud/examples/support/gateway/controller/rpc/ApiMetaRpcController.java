package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ApiMetaRpcController implements ApiMetaRpc {

	@Autowired
	private ApiMetaRpcService apiMetaRpcService;
	
	@Override
	public RespVO<Base> upload(@RequestBody @Valid ApiMetaUploadReqVO req) {
		return apiMetaRpcService.save(req);
	}

}