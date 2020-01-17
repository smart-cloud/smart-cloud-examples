package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

public class ProductInfoApiSystemTest extends AbstractSystemTest {

	@Test
	public void testPageProduct() throws IOException {
		RespVO<BasePageRespVO<PageProductRespVO>> result = ProductInfoApi.pageProduct();
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}