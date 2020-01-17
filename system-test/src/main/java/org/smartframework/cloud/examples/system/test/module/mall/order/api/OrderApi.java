package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import java.io.IOException;
import java.util.Arrays;

import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.utility.HttpUtil;

import com.alibaba.fastjson.TypeReference;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderApi {

	public RespVO<CreateOrderRespVO> create() throws IOException {
		CreateOrderProductInfoReqVO createOrderProductInfoReqVO = new CreateOrderProductInfoReqVO();
		// TODO:调用api创建商品；查询商品
		createOrderProductInfoReqVO.setProductId(398919302406737920L);
		createOrderProductInfoReqVO.setBuyCount(10);

		CreateOrderReqVO createOrderReqVO = new CreateOrderReqVO();
		createOrderReqVO.setProducts(Arrays.asList(createOrderProductInfoReqVO));

		return HttpUtil.postWithRaw(SystemTestConfig.getOrderBaseUrl() + "api/identity/order/order/create",
				createOrderReqVO, new TypeReference<RespVO<CreateOrderRespVO>>() {
				});
	}

}