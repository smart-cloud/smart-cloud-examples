package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.service.rpc.GatewayAuthRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.GatewayAuthRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUpdateReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUploadReqVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class GatewayAuthRpcController implements GatewayAuthRpc {

    @Autowired
    private GatewayAuthRpcService gatewayAuthRpcService;

    @Override
    public RespVO<Base> upload(@RequestBody @Valid GatewayAuthUploadReqVO req) {
        gatewayAuthRpcService.upload(req);
        return RespUtil.success();
    }

    @Override
    public RespVO<Base> update(@RequestBody @Valid GatewayAuthUpdateReqVO req) {
        gatewayAuthRpcService.update(req);
        return RespUtil.success();
    }

}