package org.smartframework.cloud.examples.support.rpc.gateway;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUpdateReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUploadReqVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 权限rpc相关接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME, contextId = "gatewayAuthRpc")
public interface GatewayAuthRpc {

    /**
     * 上传权限信息
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/auth/upload")
    RespVO<Base> upload(@RequestBody @Valid GatewayAuthUploadReqVO req);

    /**
     * 上传权限信息（更新）
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/auth/update")
    RespVO<Base> update(@RequestBody @Valid GatewayAuthUpdateReqVO req);

}