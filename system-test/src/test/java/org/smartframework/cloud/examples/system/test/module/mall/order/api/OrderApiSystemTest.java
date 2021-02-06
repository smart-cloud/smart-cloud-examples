package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.enums.order.OrderStatus;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
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
    public void testSubmit() throws IOException {
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

        // 3、提交订单
        SubmitOrderProductInfoReqVO submitOrderProductInfoReqVO = new SubmitOrderProductInfoReqVO();
        long productId = pageProductResult.getBody().getDatas().get(0).getId();
        submitOrderProductInfoReqVO.setProductId(productId);
        submitOrderProductInfoReqVO.setBuyCount(10);

        SubmitOrderReqVO createOrderReqVO = new SubmitOrderReqVO();
        createOrderReqVO.setProducts(Arrays.asList(submitOrderProductInfoReqVO));

        RespVO<String> submitResult = OrderApi.submit(createOrderReqVO);
        Assertions.assertThat(submitResult).isNotNull();
        Assertions.assertThat(submitResult.getHead()).isNotNull();
        Assertions.assertThat(submitResult.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(submitResult.getBody()).isNotBlank();

        // 4、查询提单结果
        RespVO<QuerySubmitResultRespVO> querySubmitResultResp = OrderApi.querySubmitResult(submitResult.getBody());
        Assertions.assertThat(querySubmitResultResp).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getHead()).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(querySubmitResultResp.getBody()).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getBody().getOrderStatus()).isEqualTo(OrderStatus.PAY_TODO.getStatus());
    }

}