/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.mall.order.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Rollback
//@Transactional
class OrderApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private ProductInfoRpc productInfoRpc;

    @Test
    void testSubmit() throws Exception {
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

        Response<String> submitResponse = post("/order/api/order/submit", reqVO, new TypeReference<Response<String>>() {
        });

        // 3、断言结果
        Assertions.assertThat(submitResponse).isNotNull();
        Assertions.assertThat(submitResponse.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(submitResponse.getBody()).isNotBlank();

        // 4、查询提单结果
        TimeUnit.SECONDS.sleep(10);
        Response<QuerySubmitResultRespVO> response = querySubmitResult(submitResponse.getBody());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testQuerySubmitResult() throws Exception {
        String orderNo = OrderUtil.generateOrderNo(1L);
        Response<QuerySubmitResultRespVO> resp = querySubmitResult(orderNo);

        Assertions.assertThat(resp).isNotNull();
        Assertions.assertThat(resp.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

    private Response<QuerySubmitResultRespVO> querySubmitResult(String orderNo) throws Exception {
        return get("/order/api/order/querySubmitResult?orderNo=" + orderNo, null, new TypeReference<Response<QuerySubmitResultRespVO>>() {
        });
    }

    private void mockStubbing(ProductInfoRpc productInfoRpc, List<SubmitOrderProductInfoReqVO> buyProducts) {
        // 2.1qryProductByIds
        // response
        List<QryProductByIdRespDTO> productInfos = new ArrayList<>();
        for (SubmitOrderProductInfoReqVO buyProduct : buyProducts) {
            Long productId = buyProduct.getProductId();

            // response
            QryProductByIdRespDTO qryProductByIdRespDTO = new QryProductByIdRespDTO();
            qryProductByIdRespDTO.setId(productId);
            qryProductByIdRespDTO.setName("手机" + productId);
            qryProductByIdRespDTO.setSellPrice(productId * 10000);
            qryProductByIdRespDTO.setStock(productId * 10000);
            productInfos.add(qryProductByIdRespDTO);
        }
        QryProductByIdsRespDTO qryProductByIdsRespVO = new QryProductByIdsRespDTO(productInfos);
        // stubbing
        Mockito.when(productInfoRpc.qryProductByIds(Mockito.any())).thenReturn(ResponseUtil.success(qryProductByIdsRespVO));

        // 2.2updateStock
        // stubbing
        Mockito.when(productInfoRpc.updateStock(Mockito.any())).thenReturn(ResponseUtil.success());
    }

}