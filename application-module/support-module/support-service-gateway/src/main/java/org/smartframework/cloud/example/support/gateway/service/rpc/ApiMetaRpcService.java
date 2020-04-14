package org.smartframework.cloud.example.support.gateway.service.rpc;

import java.util.Map;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.example.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO.ApiAC;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiMetaRpcService {

	@Autowired
	private RedisComponent redisComponent;

	/**
	 * 保存接口元数据
	 * 
	 * @param req
	 * @return
	 */
	public RespVO<Base> save(ApiMetaUploadReqVO req) {
		Map<String, ApiAC> apiACs = req.getApiACs();

		// redis持久化
		apiACs.forEach((urlMethod, apiAC) -> {
			String cachekey = RedisKeyHelper.getApiMetaKey(urlMethod);
			redisComponent.setObject(cachekey, apiAC, null);
		});

		return RespUtil.success();
	}

}