package org.smartframework.cloud.example.support.gateway.service.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUpdateReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.GatewayAuthUploadReqVO;
import org.springframework.stereotype.Service;

/**
 * @desc 权限信息处理
 * @author liyulin
 * @date 2020/04/28
 */
@Service
public class GatewayAuthRpcService {

	public RespVO<Base> upload(GatewayAuthUploadReqVO req) {
		return null;
	}

	public RespVO<Base> update(GatewayAuthUpdateReqVO req) {
		return null;
	}

}