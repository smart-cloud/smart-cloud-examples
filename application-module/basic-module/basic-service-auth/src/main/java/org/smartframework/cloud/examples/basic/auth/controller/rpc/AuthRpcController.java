package org.smartframework.cloud.examples.basic.auth.controller.rpc;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.rpc.AuthRpcService;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthRpcController implements AuthRpc {

    @Autowired
    private AuthRpcService authRpcService;

    @Override
    public Response<AuthRespVO> listByUid(Long uid) {
        return RespUtil.success(authRpcService.listByUid(uid));
    }

}