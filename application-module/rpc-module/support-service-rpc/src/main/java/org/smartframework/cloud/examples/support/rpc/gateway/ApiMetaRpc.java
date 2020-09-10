package org.smartframework.cloud.examples.support.rpc.gateway;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 接口元数据rpc相关接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME, contextId = "apiMetaRpc")
public interface ApiMetaRpc {

    /**
     * 上传接口元数据
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/apiMeta/upload")
    RespVO<Base> upload(@RequestBody @Valid ApiMetaUploadReqVO req);

}