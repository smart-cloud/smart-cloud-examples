package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.starter.test.AbstractSystemTest;
import org.smartframework.cloud.utility.RandomUtil;

import java.io.IOException;

/**
 * @author liyulin
 * @date 2020-10-10
 */
public class ProductInfoOmsSystemTest extends AbstractSystemTest {

    @Test
    public void testCreate() throws IOException {
        ProductInsertReqVO reqVO = new ProductInsertReqVO();
        reqVO.setName("商品" + RandomUtil.generateRandom(false, 10));
        reqVO.setSellPrice(1000L);
        reqVO.setStock(100L);
        Response<Boolean> result = ProductInfoOms.create(reqVO);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isTrue();
    }

}