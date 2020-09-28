package org.smartframework.cloud.examples.mall.order.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.app.auth.core.UserBO;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Rollback
@Transactional
public class OrderApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private ProductInfoRpc productInfoRpc;

    @Test
    public void testCreate() throws Exception {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());
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
        mockStubbing(productInfoRpc, buyProducts);

        RespVO<CreateOrderRespVO> resp = post("/order/api/order/create", reqVO, new TypeReference<RespVO<CreateOrderRespVO>>() {
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
        for (CreateOrderProductInfoReqVO buyProduct : buyProducts) {
            Long productId = buyProduct.getProductId();

            // response
            QryProductByIdRespVO qryProductByIdRespVO = new QryProductByIdRespVO();
            qryProductByIdRespVO.setId(productId);
            qryProductByIdRespVO.setName("手机" + productId);
            qryProductByIdRespVO.setSellPrice(productId * 10000);
            qryProductByIdRespVO.setStock(productId * 10000);
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