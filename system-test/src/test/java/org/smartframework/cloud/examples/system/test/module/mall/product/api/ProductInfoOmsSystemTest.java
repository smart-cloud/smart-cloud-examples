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
package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.starter.test.AbstractSystemTest;
import io.github.smart.cloud.utility.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;

import java.io.IOException;

/**
 * @author collin
 * @date 2020-10-10
 */
class ProductInfoOmsSystemTest extends AbstractSystemTest {

    @Test
    void testCreate() throws IOException {
        ProductInsertReqVO reqVO = new ProductInsertReqVO();
        reqVO.setName("商品" + RandomUtil.generateRandom(false, 10));
        reqVO.setSellPrice(1000L);
        reqVO.setStock(100L);
        Response<Boolean> result = ProductInfoOms.create(reqVO);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isTrue();
    }

}