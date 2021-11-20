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
package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
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

class OrderApiSystemTest extends AbstractSystemTest {

    @Test
    void testSubmit() throws IOException {
        // 1、创建商品
        ProductInsertReqVO productInsertReqVO = new ProductInsertReqVO();
        String name = "商品" + RandomUtil.generateRandom(false, 10);
        productInsertReqVO.setName(name);
        productInsertReqVO.setSellPrice(1000L);
        productInsertReqVO.setStock(100L);
        Response<Boolean> createResult = ProductInfoOms.create(productInsertReqVO);
        Assertions.assertThat(createResult).isNotNull();
        Assertions.assertThat(createResult.getHead()).isNotNull();
        Assertions.assertThat(createResult.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(createResult.getBody()).isTrue();

        // 2、查询商品
        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setName(name);
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);
        Response<BasePageResponse<PageProductRespVO>> pageProductResult = ProductInfoApi.pageProduct(reqVO);
        Assertions.assertThat(pageProductResult).isNotNull();
        Assertions.assertThat(pageProductResult.getHead()).isNotNull();
        Assertions.assertThat(pageProductResult.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(pageProductResult.getBody()).isNotNull();
        Assertions.assertThat(pageProductResult.getBody().getDatas()).isNotEmpty();

        // 3、提交订单
        SubmitOrderProductInfoReqVO submitOrderProductInfoReqVO = new SubmitOrderProductInfoReqVO();
        long productId = pageProductResult.getBody().getDatas().get(0).getId();
        submitOrderProductInfoReqVO.setProductId(productId);
        submitOrderProductInfoReqVO.setBuyCount(10);

        SubmitOrderReqVO createOrderReqVO = new SubmitOrderReqVO();
        createOrderReqVO.setProducts(Arrays.asList(submitOrderProductInfoReqVO));

        Response<String> submitResult = OrderApi.submit(createOrderReqVO);
        Assertions.assertThat(submitResult).isNotNull();
        Assertions.assertThat(submitResult.getHead()).isNotNull();
        Assertions.assertThat(submitResult.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(submitResult.getBody()).isNotBlank();

        // 4、查询提单结果
        Response<QuerySubmitResultRespVO> querySubmitResultResp = OrderApi.querySubmitResult(submitResult.getBody());
        Assertions.assertThat(querySubmitResultResp).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getHead()).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(querySubmitResultResp.getBody()).isNotNull();
        Assertions.assertThat(querySubmitResultResp.getBody().getOrderStatus()).isEqualTo(OrderStatus.PAY_TODO.getStatus());
    }

}