package org.smartframework.cloud.examples.basic.rpc.auth;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.basic.rpc.constant.RpcConstants;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;

/**
 * 权限rpc相关接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Auth.FEIGN_CLIENT_NAME, contextId = "authRpc")
public interface AuthRpc {

    /**
     * 根据uid查询用户拥有的权限信息
     *
     * @param uid
     * @return
     */
    @GetMapping("auth/rpc/auth/listByUid")
    Response<AuthRespDTO> listByUid(@NotNull Long uid);

}