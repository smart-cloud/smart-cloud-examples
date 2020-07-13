package org.smartframework.cloud.examples.support.rpc.gateway;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME, contextId = "apiMetaRpc")
@Api(tags = "接口元数据rpc相关接口")
@ApiIgnore
public interface ApiMetaRpc {

    @ApiOperation("上传接口元数据")
    @PostMapping("gateway/rpc/apiMeta/upload")
    RespVO<Base> upload(@RequestBody @Valid ApiMetaUploadReqVO req);

}