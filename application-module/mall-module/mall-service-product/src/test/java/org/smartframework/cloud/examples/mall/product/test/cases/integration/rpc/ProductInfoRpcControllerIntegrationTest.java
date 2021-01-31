package org.smartframework.cloud.examples.mall.product.test.cases.integration.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.test.data.ProductInfoData;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Rollback
@Transactional
public class ProductInfoRpcControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private ProductInfoData productInfoData;

    @Test
    public void testQryProductById() throws Exception {
        Long productId = 200L;
        productInfoData.insertTestData(productId);
        QryProductByIdReqVO reqBody = new QryProductByIdReqVO();
        reqBody.setId(productId);

        RespVO<QryProductByIdRespVO> result = super.get(
                "/product/rpc/productInfo/qryProductById", reqBody,
                new TypeReference<RespVO<QryProductByIdRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
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

        RespVO<QryProductByIdsRespVO> result = super.get(
                "/product/rpc/productInfo/qryProductByIds", qryProductByIdsReqVO,
                new TypeReference<RespVO<QryProductByIdsRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
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

        RespVO<Base> result = super.post("/product/rpc/productInfo/updateStock",
                updateStockReqVO, new TypeReference<RespVO<Base>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

}