package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class ApiMetaRpcController implements ApiMetaRpc {

    @Autowired
    private ApiMetaRpcService apiMetaRpcService;

    @Override
    public RespVO<Base> upload(@RequestBody @Valid ApiMetaUploadReqVO req) {
        apiMetaRpcService.save(req);
        return RespUtil.success();
    }

}