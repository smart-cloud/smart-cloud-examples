package org.smartframework.cloud.examples.mall.product.test.cases.integration.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.common.business.util.ReqUtil;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

@Rollback
@Transactional
public class ProductInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();

		RespVO<BasePageRespVO<PageProductRespVO>> result = super.postWithNoHeaders("/api/identity/product/productInfo/pageProduct",
				ReqUtil.build(null, 1, 10), new TypeReference<RespVO<BasePageRespVO<PageProductRespVO>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}