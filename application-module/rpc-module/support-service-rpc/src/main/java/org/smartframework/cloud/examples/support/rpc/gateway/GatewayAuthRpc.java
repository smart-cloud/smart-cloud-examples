package org.smartframework.cloud.examples.support.rpc.gateway;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUpdateReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUploadReqVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME)
@Api(tags = "权限rpc相关接口")
@ApiIgnore
public interface GatewayAuthRpc {
	
	@ApiOperation("上传权限信息")
	@PostMapping("gateway/rpc/auth/upload")
	RespVO<Base> upload(@RequestBody @Valid GatewayAuthUploadReqVO req);
	
	@ApiOperation("上传权限信息")
	@PostMapping("gateway/rpc/auth/update")
	RespVO<Base> update(@RequestBody @Valid GatewayAuthUpdateReqVO req);

}