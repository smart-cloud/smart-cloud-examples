package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import java.io.IOException;
import java.util.Arrays;

import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderProductInfoReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.api.CreateOrderRespBody;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.utility.HttpUtil;

import com.alibaba.fastjson.TypeReference;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderApi {

	public Resp<CreateOrderRespBody> create() throws IOException {
		CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
		// TODO:调用api创建商品；查询商品
		createOrderProductInfoReqBody.setProductId(398919302406737920L);
		createOrderProductInfoReqBody.setBuyCount(10);

		CreateOrderReqBody createOrderReqBody = new CreateOrderReqBody();
		createOrderReqBody.setProducts(Arrays.asList(createOrderProductInfoReqBody));

		return HttpUtil.postWithRaw(SystemTestConfig.getOrderBaseUrl() + "api/identity/order/order/create",
				createOrderReqBody, new TypeReference<Resp<CreateOrderRespBody>>() {
				});
	}

}