package org.smartframework.cloud.examples.mall.order.test.cases.integration.api;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.test.AbstractIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

@Rollback
@Transactional
public class OrderApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testCreate() throws Exception {
		// 1、构建请求
		// build args
		List<CreateOrderProductInfoReqVO> buyProducts = new ArrayList<>();
		for (long i = 1; i <= 3; i++) {
			CreateOrderProductInfoReqVO createOrderProductInfoReqVO = new CreateOrderProductInfoReqVO();
			createOrderProductInfoReqVO.setProductId(i);
			createOrderProductInfoReqVO.setBuyCount((int) i);

			buyProducts.add(createOrderProductInfoReqVO);
		}
		
		CreateOrderReqVO reqVO = new CreateOrderReqVO();
		reqVO.setProducts(buyProducts);
				
		// 2、mock 行为
		ProductInfoRpc productInfoRpc = Mockito.mock(ProductInfoRpc.class);
		OrderApiService orderApiService = applicationContext.getBean(OrderApiService.class);
		setMockAttribute(orderApiService, productInfoRpc);
		mockStubbing(productInfoRpc, buyProducts);
		
		RespVO<CreateOrderRespVO> resp = postWithNoHeaders("/order/api/order/create", reqVO, new TypeReference<RespVO<CreateOrderRespVO>>() {
		});

		// 3、断言结果
		Assertions.assertThat(resp).isNotNull();
		Assertions.assertThat(resp.getHead()).isNotNull();
		Assertions.assertThat(resp.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}
	
	private void mockStubbing(ProductInfoRpc productInfoRpc, List<CreateOrderProductInfoReqVO> buyProducts) {
		// 2.1qryProductByIds
		// response
		List<QryProductByIdRespVO> productInfos = new ArrayList<>();
		for(CreateOrderProductInfoReqVO buyProduct:buyProducts){
			Long productId = buyProduct.getProductId();
			
			// response
			QryProductByIdRespVO qryProductByIdRespVO = new QryProductByIdRespVO();
			qryProductByIdRespVO.setId(productId);
			qryProductByIdRespVO.setName("手机"+productId);
			qryProductByIdRespVO.setSellPrice(productId*10000);
			qryProductByIdRespVO.setStock(productId*10000);
			productInfos.add(qryProductByIdRespVO);
		}
		QryProductByIdsRespVO qryProductByIdsRespVO = new QryProductByIdsRespVO(productInfos);
		// stubbing
		Mockito.when(productInfoRpc.qryProductByIds(Mockito.any())).thenReturn(RespUtil.success(qryProductByIdsRespVO));
		
		// 2.2updateStock
		// stubbing
		Mockito.when(productInfoRpc.updateStock(Mockito.any())).thenReturn(RespUtil.success());
	}

}