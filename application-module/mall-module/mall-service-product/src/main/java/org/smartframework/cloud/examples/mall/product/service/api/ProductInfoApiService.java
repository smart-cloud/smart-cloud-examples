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
package org.smartframework.cloud.examples.mall.product.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.biz.api.ProductInfoApiBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.springframework.stereotype.Service;

/**
 * 商品信息 api service
 *
 * @author collin
 * @date 2019-03-29
 */
@Service
@RequiredArgsConstructor
@DS(DataSourceName.MALL_PRODUCT_MASTER)
public class ProductInfoApiService {

    private final ProductInfoApiBiz productOmsBiz;

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    @DS(DataSourceName.MALL_PRODUCT_SLAVE)
    public BasePageResponse<PageProductRespVO> pageProduct(PageProductReqVO req) {
        return productOmsBiz.pageProduct(req);
    }

}