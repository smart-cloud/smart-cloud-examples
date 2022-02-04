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
package org.smartframework.cloud.examples.mall.product.biz.rpc;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.mall.product.entity.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.product.mapper.rpc.ProductInfoRpcMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.smartframework.cloud.utility.ObjectUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息rpc biz
 *
 * @author collin
 * @date 2019-03-31
 */
@Repository
@RequiredArgsConstructor
public class ProductInfoRpcBiz extends BaseBiz<ProductInfoBaseMapper, ProductInfoEntity> {

    private final ProductInfoRpcMapper productInfoRpcMapper;

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    public QryProductByIdRespDTO qryProductById(QryProductByIdReqDTO reqBody) {
        ProductInfoEntity entity = super.getById(reqBody.getId());
        if (ObjectUtil.isNull(entity)) {
            return null;
        }

        return QryProductByIdRespDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellPrice(entity.getSellPrice())
                .stock(entity.getStock())
                .build();
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqDTO
     * @return
     */
    public QryProductByIdsRespDTO qryProductByIds(QryProductByIdsReqDTO reqDTO) {
        List<ProductInfoEntity> entities = super.listByIds(reqDTO.getIds());
        if (ObjectUtil.isNull(entities)) {
            return null;
        }

        List<QryProductByIdRespDTO> productInfos = entities.stream()
                .map(entity -> QryProductByIdRespDTO.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .sellPrice(entity.getSellPrice())
                        .stock(entity.getStock())
                        .build())
                .collect(Collectors.toList());

        return new QryProductByIdsRespDTO(productInfos);
    }

    /**
     * 扣减库存
     *
     * @param list
     * @return
     */
    public boolean updateStock(List<UpdateStockItem> list) {
        return productInfoRpcMapper.updateStock(list) > 0;
    }

}