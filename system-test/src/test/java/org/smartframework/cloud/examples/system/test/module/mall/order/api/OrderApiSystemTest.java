package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.examples.system.test.module.mall.product.api.ProductInfoApi;
import org.smartframework.cloud.examples.system.test.module.mall.product.api.ProductInfoOms;
import org.smartframework.cloud.starter.test.AbstractSystemTest;
import org.smartframework.cloud.utility.RandomUtil;

import java.io.IOException;
import java.util.Arrays;

public class OrderApiSystemTest extends AbstractSystemTest {

    @Test
    public void testCreate() throws IOException {
        // 1、创建商品
        ProductInsertReqVO productInsertReqVO = new ProductInsertReqVO();
        String name = "商品" + RandomUtil.generateRandom(false, 10);
        productInsertReqVO.setName(name);
        productInsertReqVO.setSellPrice(1000L);
        productInsertReqVO.setStock(100L);
        RespVO<Base> createResult = ProductInfoOms.create(productInsertReqVO);

        // 2、查询商品
        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setName(name);
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);
        RespVO<BasePageRespVO<PageProductRespVO>> pageProductResult = ProductInfoApi.pageProduct(reqVO);

        CreateOrderProductInfoReqVO createOrderProductInfoReqVO = new CreateOrderProductInfoReqVO();
        long productId = pageProductResult.getBody().getDatas().get(0).getId();
        createOrderProductInfoReqVO.setProductId(productId);
        createOrderProductInfoReqVO.setBuyCount(10);

        CreateOrderReqVO createOrderReqVO = new CreateOrderReqVO();
        createOrderReqVO.setProducts(Arrays.asList(createOrderProductInfoReqVO));

        RespVO<CreateOrderRespVO> result = OrderApi.create(createOrderReqVO);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

}