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
package org.smartframework.cloud.examples.mall.product.service.rpc;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.biz.rpc.ProductInfoRpcBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.stereotype.Service;

/**
 * 商品信息rpc service
 *
 * @author collin
 * @date 2019-03-29
 */
@Service
@RequiredArgsConstructor
@DS(DataSourceName.MALL_PRODUCT_MASTER)
public class ProductInfoRpcService {

    private final ProductInfoRpcBiz productRpcBiz;

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    @DS(DataSourceName.MALL_PRODUCT_SLAVE)
    public QryProductByIdRespDTO qryProductById(QryProductByIdReqDTO reqBody) {
        return productRpcBiz.qryProductById(reqBody);
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqDTO
     * @return
     */
    @DS(DataSourceName.MALL_PRODUCT_SLAVE)
    public QryProductByIdsRespDTO qryProductByIds(QryProductByIdsReqDTO reqDTO) {
        return productRpcBiz.qryProductByIds(reqDTO);
    }

    /**
     * 扣减库存
     *
     * @param req
     * @return
     */
    @DSTransactional
    public Boolean updateStock(UpdateStockReqDTO req) {
        return productRpcBiz.updateStock(req.getItems());
    }

}