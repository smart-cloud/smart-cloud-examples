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
package org.smartframework.cloud.examples.mall.product.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.constants.CommonReturnCodes;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class ProductInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private ProductInfoData productInfoData;

    @Test
    void testCreate() throws Exception {
        ProductInsertReqVO productInsertReqVO = new ProductInsertReqVO();
        productInsertReqVO.setName("iphone10");
        productInsertReqVO.setSellPrice(10000L);
        productInsertReqVO.setStock(200L);

        Response<Boolean> result = super.post("/product/oms/productInfo/create", productInsertReqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testUpdate() throws Exception {
        Long productId = 1L;
        productInfoData.insertTestData(productId);

        ProductUpdateReqVO productUpdateReqVO = new ProductUpdateReqVO();
        productUpdateReqVO.setId(productId);
        productUpdateReqVO.setName("iphone10");
        productUpdateReqVO.setSellPrice(10000L);
        productUpdateReqVO.setStock(200L);

        Response<Boolean> result = super.post("/product/oms/productInfo/update", productUpdateReqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testLogicDelete() throws Exception {
        Long productId = 2L;
        productInfoData.insertTestData(productId);

        ProductDeleteReqVO productDeleteReqVO = new ProductDeleteReqVO();
        productDeleteReqVO.setId(productId);

        Response<Boolean> result = super.post("/product/oms/productInfo/logicDelete",
                productDeleteReqVO, new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testPageProduct() throws Exception {
        productInfoData.batchInsertTestData();

        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setName("iphone");
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        Response<BasePageResponse<ProductInfoBaseRespVO>> result = super.get(
                "/product/oms/productInfo/pageProduct", reqVO,
                new TypeReference<Response<BasePageResponse<ProductInfoBaseRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}