package org.smartframework.cloud.example.support.gateway.controller.rpc;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ApiMetaRpcController implements ApiMetaRpc {

	@Override
	public RespVO<Base> upload(@RequestBody @Valid ApiMetaUploadReqVO req) {
		return null;
	}

}