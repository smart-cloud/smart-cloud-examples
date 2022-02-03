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
package org.smartframework.cloud.examples.mall.product.controller.rpc;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.product.enums.ProductReturnCodes;
import org.smartframework.cloud.examples.mall.product.service.rpc.ProductInfoRpcService;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class ProductInfoRpcController implements ProductInfoRpc {

    private final ProductInfoRpcService productInfoRpcService;

    @Override
    public Response<QryProductByIdRespDTO> qryProductById(QryProductByIdReqDTO req) {
        return RespUtil.success(productInfoRpcService.qryProductById(req));
    }

    @Override
    public Response<QryProductByIdsRespDTO> qryProductByIds(QryProductByIdsReqDTO reqDTO) {
        return RespUtil.success(productInfoRpcService.qryProductByIds(reqDTO));
    }

    @Override
    public Response<Base> updateStock(UpdateStockReqDTO req) {
        boolean success = productInfoRpcService.updateStock(req);
        return success ? RespUtil.success() : RespUtil.error(ProductReturnCodes.STOCK_NOT_ENOUGH);
    }

}