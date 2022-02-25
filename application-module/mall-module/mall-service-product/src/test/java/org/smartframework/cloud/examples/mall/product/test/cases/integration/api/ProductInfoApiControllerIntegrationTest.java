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
package org.smartframework.cloud.examples.mall.product.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class ProductInfoApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private ProductInfoData productInfoData;

    @Test
    void testPageProduct() throws Exception {
        productInfoData.batchInsertTestData();

        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        Response<BasePageResponse<PageProductRespVO>> result = super.get("/product/api/productInfo/pageProduct",
                reqVO, new TypeReference<Response<BasePageResponse<PageProductRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}