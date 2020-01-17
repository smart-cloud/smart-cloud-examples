package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

public class OrderApiSystemTest extends AbstractSystemTest {

	@Test
	public void testCreate() throws IOException {
		RespVO<CreateOrderRespVO> result = OrderApi.create();
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}