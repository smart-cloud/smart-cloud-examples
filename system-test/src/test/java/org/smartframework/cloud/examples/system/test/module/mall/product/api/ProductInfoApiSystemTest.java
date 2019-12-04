package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.api.PageProductRespBody;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

public class ProductInfoApiSystemTest extends AbstractSystemTest {

	@Test
	public void testPageProduct() throws IOException {
		Resp<BasePageResp<PageProductRespBody>> result = ProductInfoApi.pageProduct();
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}