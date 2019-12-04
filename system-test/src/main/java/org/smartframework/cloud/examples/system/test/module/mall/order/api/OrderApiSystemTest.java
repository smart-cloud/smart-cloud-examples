package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import java.io.IOException;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderProductInfoReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.api.CreateOrderRespBody;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.starter.test.AbstractSystemTest;
import org.smartframework.cloud.utility.HttpUtil;

import com.alibaba.fastjson.TypeReference;

public class OrderApiSystemTest extends AbstractSystemTest {

	@Test
	public void testCreate() throws IOException {
		CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
		createOrderProductInfoReqBody.setProductId(398919302406737920L);
		createOrderProductInfoReqBody.setBuyCount(10);
		
		CreateOrderReqBody createOrderReqBody = new CreateOrderReqBody();
		createOrderReqBody.setProducts(Arrays.asList(createOrderProductInfoReqBody));
		
		Resp<CreateOrderRespBody> result = HttpUtil.postWithRaw(
				SystemTestConfig.getOrderBaseUrl() + "api/identity/order/order/create",
				createOrderReqBody, new TypeReference<Resp<CreateOrderRespBody>>() {
				});
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}