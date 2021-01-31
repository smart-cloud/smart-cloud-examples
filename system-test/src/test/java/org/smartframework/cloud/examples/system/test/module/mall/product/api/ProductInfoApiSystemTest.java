package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

import java.io.IOException;

public class ProductInfoApiSystemTest extends AbstractSystemTest {

    @Test
    public void testPageProduct() throws IOException {
        PageProductReqVO reqVO = new PageProductReqVO();
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);
        RespVO<BasePageRespVO<PageProductRespVO>> result = ProductInfoApi.pageProduct(reqVO);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

}