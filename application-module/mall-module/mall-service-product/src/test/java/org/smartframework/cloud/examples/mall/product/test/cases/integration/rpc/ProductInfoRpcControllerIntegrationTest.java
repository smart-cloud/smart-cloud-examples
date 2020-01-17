package org.smartframework.cloud.examples.mall.product.test.cases.integration.rpc;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

@Rollback
@Transactional
public class ProductInfoRpcControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testQryProductById() throws Exception {
		Long productId = 200L;
		productInfoData.insertTestData(productId);
		QryProductByIdReqVO reqBody = new QryProductByIdReqVO();
		reqBody.setId(productId);

		RespVO<QryProductByIdRespVO> result = super.postWithNoHeaders(
				"/rpc/identity/product/productInfo/qryProductById", reqBody,
				new TypeReference<RespVO<QryProductByIdRespVO>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testQryProductByIds() throws Exception {
		List<Long> ids = new ArrayList<>();
		for (long id = 200; id < 211; id++) {
			ids.add(id);
		}
		productInfoData.batchInsertTestData(ids);

		QryProductByIdsReqVO qryProductByIdsReqVO = new QryProductByIdsReqVO();
		qryProductByIdsReqVO.setIds(ids);

		RespVO<QryProductByIdsRespVO> result = super.postWithNoHeaders(
				"/rpc/identity/product/productInfo/qryProductByIds", qryProductByIdsReqVO,
				new TypeReference<RespVO<QryProductByIdsRespVO>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getProductInfos()).isNotEmpty();
	}

	@Test
	public void testUpdateStock() throws Exception {
		// create test data
		List<Long> ids = new ArrayList<>();
		for (long id = 300; id < 301; id++) {
			ids.add(id);
		}
		productInfoData.batchInsertTestData(ids);

		// build req params
		List<UpdateStockItem> updateStockItems = new ArrayList<>();
		for (Long id : ids) {
			updateStockItems.add(new UpdateStockItem(id, 3));
		}
		UpdateStockReqVO updateStockReqVO = new UpdateStockReqVO(updateStockItems);

		RespVO<Base> result = super.postWithNoHeaders("/rpc/identity/product/productInfo/updateStock",
				updateStockReqVO, new TypeReference<RespVO<Base>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}