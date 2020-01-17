package org.smartframework.cloud.examples.mall.product.test.cases.integration.oms;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
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
		ProductInsertReqVO productInsertReqVO = new ProductInsertReqVO();
		productInsertReqVO.setName("iphone10");
		productInsertReqVO.setSellPrice(10000L);
		productInsertReqVO.setStock(200L);

		RespVO<Base> result = super.postWithNoHeaders("/oms/auth/product/productInfo/create", productInsertReqVO,
				new TypeReference<RespVO<Base>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testUpdate() throws Exception {
		Long productId = 1L;
		productInfoData.insertTestData(productId);

		ProductUpdateReqVO productUpdateReqVO = new ProductUpdateReqVO();
		productUpdateReqVO.setId(productId);
		productUpdateReqVO.setName("iphone10");
		productUpdateReqVO.setSellPrice(10000L);
		productUpdateReqVO.setStock(200L);

		RespVO<Base> result = super.postWithNoHeaders("/oms/auth/product/productInfo/update", productUpdateReqVO,
				new TypeReference<RespVO<Base>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testLogicDelete() throws Exception {
		Long productId = 2L;
		productInfoData.insertTestData(productId);

		ProductDeleteReqVO productDeleteReqVO = new ProductDeleteReqVO();
		productDeleteReqVO.setId(productId);

		RespVO<Base> result = super.postWithNoHeaders("/oms/auth/product/productInfo/logicDelete",
				productDeleteReqVO, new TypeReference<RespVO<Base>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();

		RespVO<BasePageRespVO<ProductInfoBaseRespVO>> result = super.postWithNoHeaders(
				"/oms/auth/product/productInfo/pageProduct", ReqUtil.build(null, 1, 10),
				new TypeReference<RespVO<BasePageRespVO<ProductInfoBaseRespVO>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}