package org.smartframework.cloud.examples.support.rpc.gateway;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 用户rpc相关接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME, contextId = "userRpc")
public interface UserRpc {

    /**
     * 登录（或注册）成功后缓存用户信息
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/user/cacheUserInfo")
    public RespVO<Base> cacheUserInfo(@RequestBody @Valid CacheUserInfoReqVO req);

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/user/exit")
    public RespVO<Base> exit(@RequestBody @Valid ExitLoginReqVO req);

}
