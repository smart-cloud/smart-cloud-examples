package org.smartframework.cloud.examples.mall.order.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
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
import java.util.concurrent.TimeUnit;

@Rollback
@Transactional
public class OrderApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private ProductInfoRpc productInfoRpc;

    @Test
    public void testSubmit() throws Exception {
        // 1、构建请求
        // build args
        List<SubmitOrderProductInfoReqVO> buyProducts = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            SubmitOrderProductInfoReqVO submitOrderProductInfoReqVO = new SubmitOrderProductInfoReqVO();
            submitOrderProductInfoReqVO.setProductId(i);
            submitOrderProductInfoReqVO.setBuyCount((int) i);

            buyProducts.add(submitOrderProductInfoReqVO);
        }

        SubmitOrderReqVO reqVO = new SubmitOrderReqVO();
        reqVO.setProducts(buyProducts);

        // 2、mock 行为
        mockStubbing(productInfoRpc, buyProducts);

        RespVO<String> submitResp = post("/order/api/order/submit", reqVO, new TypeReference<RespVO<String>>() {
        });

        // 3、断言结果
        Assertions.assertThat(submitResp).isNotNull();
        Assertions.assertThat(submitResp.getHead()).isNotNull();
        Assertions.assertThat(submitResp.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(submitResp.getBody()).isNotBlank();

        // 4、查询提单结果
        TimeUnit.SECONDS.sleep(5);
        RespVO<QuerySubmitResultRespVO> resp = querySubmitResult(submitResp.getBody());

        Assertions.assertThat(resp).isNotNull();
        Assertions.assertThat(resp.getHead()).isNotNull();
        Assertions.assertThat(resp.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(resp.getBody()).isNotNull();
    }

    @Test
    public void testQuerySubmitResult() throws Exception {
        String orderNo = OrderUtil.generateOrderNo(1L);
        RespVO<QuerySubmitResultRespVO> resp = querySubmitResult(orderNo);

        Assertions.assertThat(resp).isNotNull();
        Assertions.assertThat(resp.getHead()).isNotNull();
        Assertions.assertThat(resp.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

    private RespVO<QuerySubmitResultRespVO> querySubmitResult(String orderNo) throws Exception {
        return get("/order/api/order/querySubmitResult?orderNo=" + orderNo, null, new TypeReference<RespVO<QuerySubmitResultRespVO>>() {
        });
    }

    private void mockStubbing(ProductInfoRpc productInfoRpc, List<SubmitOrderProductInfoReqVO> buyProducts) {
        // 2.1qryProductByIds
        // response
        List<QryProductByIdRespVO> productInfos = new ArrayList<>();
        for (SubmitOrderProductInfoReqVO buyProduct : buyProducts) {
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