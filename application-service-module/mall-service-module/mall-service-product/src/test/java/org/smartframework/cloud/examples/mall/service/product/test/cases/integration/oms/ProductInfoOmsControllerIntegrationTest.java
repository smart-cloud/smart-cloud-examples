package org.smartframework.cloud.examples.mall.service.product.test.cases.integration.oms;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductDeleteReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductInsertReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductUpdateReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.base.ProductInfoBaseRespBody;
import org.smartframework.cloud.starter.common.business.util.ReqUtil;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

@Rollback
@Transactional
public class ProductInfoOmsControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testCreate() throws Exception {
		ProductInsertReqBody productInsertReqBody = new ProductInsertReqBody();
		productInsertReqBody.setName("iphone10");
		productInsertReqBody.setSellPrice(10000L);
		productInsertReqBody.setStock(200L);

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/create", productInsertReqBody,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testUpdate() throws Exception {
		Long productId = 1L;
		productInfoData.insertTestData(productId);

		ProductUpdateReqBody productUpdateReqBody = new ProductUpdateReqBody();
		productUpdateReqBody.setId(productId);
		productUpdateReqBody.setName("iphone10");
		productUpdateReqBody.setSellPrice(10000L);
		productUpdateReqBody.setStock(200L);

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/update", productUpdateReqBody,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testLogicDelete() throws Exception {
		Long productId = 2L;
		productInfoData.insertTestData(productId);

		ProductDeleteReqBody productDeleteReqBody = new ProductDeleteReqBody();
		productDeleteReqBody.setId(productId);

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/logicDelete",
				productDeleteReqBody, new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();

		Resp<BasePageResp<ProductInfoBaseRespBody>> result = super.postWithNoHeaders(
				"/oms/auth/product/productInfo/pageProduct", ReqUtil.build(null, 1, 10),
				new TypeReference<Resp<BasePageResp<ProductInfoBaseRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}