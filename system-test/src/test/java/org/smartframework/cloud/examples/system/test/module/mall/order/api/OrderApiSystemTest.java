package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.api.CreateOrderRespBody;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

public class OrderApiSystemTest extends AbstractSystemTest {

	@Test
	public void testCreate() throws IOException {
		Resp<CreateOrderRespBody> result = OrderApi.create();
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}