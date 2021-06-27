package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@Validated
@Slf4j
public class ApiMetaRpcController implements ApiMetaRpc {

    @Autowired
    private ApiMetaRpcService apiMetaRpcService;

    @Override
    public Response<Base> notifyFetch(NotifyFetchReqVO req) {
        try {
            apiMetaRpcService.notifyFetch(req);
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("notifyFetch.fail", e);
            return RespUtil.error(e.getMessage());
        }
        return RespUtil.success();
    }

}