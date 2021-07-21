package org.smartframework.cloud.examples.mall.product.test.cases.integration.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
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

        Response<Base> result = super.post("/product/rpc/productInfo/updateStock",
                updateStockReqDTO, new TypeReference<Response<Base>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

}