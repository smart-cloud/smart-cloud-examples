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
package org.smartframework.cloud.examples.mall.product.test.cases.integration.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Rollback
@Transactional
class ProductInfoRpcControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private ProductInfoData productInfoData;

    @Test
    void testQryProductById() throws Exception {
        Long productId = 200L;
        productInfoData.insertTestData(productId);
        QryProductByIdReqDTO reqBody = new QryProductByIdReqDTO();
        reqBody.setId(productId);

        Response<QryProductByIdRespDTO> result = super.get(
                "/product/rpc/productInfo/qryProductById", reqBody,
                new TypeReference<Response<QryProductByIdRespDTO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

    @Test
    void testQryProductByIds() throws Exception {
        List<Long> ids = new ArrayList<>();
        for (long id = 200; id < 211; id++) {
            ids.add(id);
        }
        productInfoData.batchInsertTestData(ids);

        QryProductByIdsReqDTO qryProductByIdsReqDTO = new QryProductByIdsReqDTO();
        qryProductByIdsReqDTO.setIds(ids);

        Response<QryProductByIdsRespDTO> result = super.get(
                "/product/rpc/productInfo/qryProductByIds", qryProductByIdsReqDTO,
                new TypeReference<Response<QryProductByIdsRespDTO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getProductInfos()).isNotEmpty();
    }

    @Test
    void testUpdateStock() throws Exception {
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
        UpdateStockReqDTO updateStockReqDTO = new UpdateStockReqDTO(updateStockItems);

        Response<Void> result = super.post("/product/rpc/productInfo/updateStock",
                updateStockReqDTO, new TypeReference<Response<Void>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

}