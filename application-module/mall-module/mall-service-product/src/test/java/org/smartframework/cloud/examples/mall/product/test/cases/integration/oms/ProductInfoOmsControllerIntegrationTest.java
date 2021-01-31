package org.smartframework.cloud.examples.mall.product.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.app.auth.core.UserBO;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
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
public class ProductInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private ProductInfoData productInfoData;

    @Before
    public void beforeTest() {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());
    }

    @Test
    public void testCreate() throws Exception {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());

        ProductInsertReqVO productInsertReqVO = new ProductInsertReqVO();
        productInsertReqVO.setName("iphone10");
        productInsertReqVO.setSellPrice(10000L);
        productInsertReqVO.setStock(200L);

        RespVO<Base> result = super.post("/product/oms/productInfo/create", productInsertReqVO,
                new TypeReference<RespVO<Base>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

    @Test
    public void testUpdate() throws Exception {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());

        Long productId = 1L;
        productInfoData.insertTestData(productId);

        ProductUpdateReqVO productUpdateReqVO = new ProductUpdateReqVO();
        productUpdateReqVO.setId(productId);
        productUpdateReqVO.setName("iphone10");
        productUpdateReqVO.setSellPrice(10000L);
        productUpdateReqVO.setStock(200L);

        RespVO<Base> result = super.post("/product/oms/productInfo/update", productUpdateReqVO,
                new TypeReference<RespVO<Base>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

    @Test
    public void testLogicDelete() throws Exception {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());

        Long productId = 2L;
        productInfoData.insertTestData(productId);

        ProductDeleteReqVO productDeleteReqVO = new ProductDeleteReqVO();
        productDeleteReqVO.setId(productId);

        RespVO<Base> result = super.post("/product/oms/productInfo/logicDelete",
                productDeleteReqVO, new TypeReference<RespVO<Base>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

    @Test
    public void testPageProduct() throws Exception {
        productInfoData.batchInsertTestData();

        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        RespVO<BasePageRespVO<ProductInfoBaseRespVO>> result = super.get(
                "/product/oms/productInfo/pageProduct", reqVO,
                new TypeReference<RespVO<BasePageRespVO<ProductInfoBaseRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}