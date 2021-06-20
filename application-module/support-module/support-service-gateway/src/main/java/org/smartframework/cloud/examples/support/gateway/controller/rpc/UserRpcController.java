package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.support.gateway.service.rpc.UserRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户
 *
 * @author liyulin
 * @date 2020-09-11
 */
@RestController
@Validated
public class UserRpcController implements UserRpc {

    @Autowired
    private UserRpcService userRpcService;

    /**
     * 登录（或注册）成功后缓存用户信息
     *
     * @param req
     * @return
     */
    @Override
    public Response<Base> cacheUserInfo(@RequestBody @Valid CacheUserInfoReqVO req) {
        userRpcService.cacheUserInfo(req);
        return RespUtil.success();
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @Override
    public Response<Base> exit(@RequestBody @Valid ExitLoginReqVO req) {
        userRpcService.exit(req);
        return RespUtil.success();
    }

}