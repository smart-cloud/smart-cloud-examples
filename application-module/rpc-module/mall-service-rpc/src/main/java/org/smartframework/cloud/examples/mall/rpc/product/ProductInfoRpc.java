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
package org.smartframework.cloud.examples.mall.rpc.product;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品信息rpc相关接口
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Product.FEIGN_CLIENT_NAME, contextId = "productInfoRpc")
public interface ProductInfoRpc {

    /**
     * 根据id查询商品信息
     *
     * @param req
     * @return
     */
    @GetMapping("product/rpc/productInfo/qryProductById")
    Response<QryProductByIdRespDTO> qryProductById(@SpringQueryMap @Valid @NotNull QryProductByIdReqDTO req);

    /**
     * 根据ids查询商品信息
     *
     * @param reqDTO
     * @return
     */
    @GetMapping("product/rpc/productInfo/qryProductByIds")
    Response<QryProductByIdsRespDTO> qryProductByIds(@SpringQueryMap @Valid @NotNull QryProductByIdsReqDTO reqDTO);

    /**
     * 更新库存
     *
     * @param req
     * @return
     */
    @PostMapping("product/rpc/productInfo/updateStock")
    Response<Base> updateStock(@RequestBody @Valid UpdateStockReqDTO req);

}