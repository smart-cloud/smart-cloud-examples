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
package org.smartframework.cloud.examples.mall.product.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.starter.global.id.GlobalId;
import io.github.smart.cloud.starter.mybatis.plus.common.repository.BaseRepository;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.mall.product.entity.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.ProductInfoMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.entity.ProductInfoEntityRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息repository
 *
 * @author collin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoRepository extends BaseRepository<ProductInfoMapper, ProductInfoEntity> {

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<PageProductRespVO> pageProduct(PageProductReqVO req) {
        LambdaQueryWrapper<ProductInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        wrapper.like(StringUtils.isNotBlank(name), ProductInfoEntity::getName, name)
                .eq(ProductInfoEntity::getDelState, DeleteState.NORMAL)
                .orderByDesc(ProductInfoEntity::getInsertTime);
        IPage<ProductInfoEntity> page = super.page(new Page<>(req.getPageNum(), req.getPageSize(), true), wrapper);
        List<ProductInfoEntity> entitydatas = page.getRecords();

        if (CollectionUtils.isEmpty(entitydatas)) {
            return new BasePageResponse<>(null, req.getPageNum(), req.getPageSize(), 0);
        }

        List<PageProductRespVO> pagedatas = entitydatas.stream()
                .map(entity -> PageProductRespVO.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .sellPrice(entity.getSellPrice())
                        .stock(entity.getStock())
                        .build())
                .collect(Collectors.toList());

        return new BasePageResponse<>(pagedatas, req.getPageNum(), req.getPageSize(), page.getTotal());
    }

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    public QryProductByIdRespDTO qryProductById(QryProductByIdReqDTO reqBody) {
        ProductInfoEntity entity = super.getById(reqBody.getId());
        if (entity == null) {
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
        if (entities == null) {
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
    public boolean updateStock(List<UpdateStockReqDTO.UpdateStockItem> list) {
        return getBaseMapper().updateStock(list) > 0;
    }

    /**
     * 新增
     *
     * @param reqBody
     * @return
     */
    public boolean insert(ProductInsertReqVO reqBody) {
        ProductInfoEntity productInfoEntity = new ProductInfoEntity();
        productInfoEntity.setId(GlobalId.nextId());
        productInfoEntity.setInsertTime(new Date());
        productInfoEntity.setDelState(DeleteState.NORMAL);
        productInfoEntity.setName(reqBody.getName());
        productInfoEntity.setSellPrice(reqBody.getSellPrice());
        productInfoEntity.setStock(reqBody.getStock());
        productInfoEntity.setInsertUser(UserContext.getUserId());
        return super.save(productInfoEntity);
    }

    /**
     * 修改
     *
     * @param reqBody
     * @return
     */
    public boolean update(ProductUpdateReqVO reqBody) {
        ProductInfoEntity productInfoEntity = new ProductInfoEntity();
        productInfoEntity.setId(reqBody.getId());
        productInfoEntity.setName(reqBody.getName());
        productInfoEntity.setSellPrice(reqBody.getSellPrice());
        productInfoEntity.setStock(reqBody.getStock());
        productInfoEntity.setUpdTime(new Date());
        productInfoEntity.setUpdUser(UserContext.getUserId());
        return super.updateById(productInfoEntity);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public boolean logicDelete(long id) {
        return super.logicDelete(id, UserContext.getUserId());
    }

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<ProductInfoEntityRespVO> pageProduct(org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO req) {
        LambdaQueryWrapper<ProductInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        wrapper.like(StringUtils.isNotBlank(name), ProductInfoEntity::getName, name)
                .eq(ProductInfoEntity::getDelState, DeleteState.NORMAL)
                .orderByDesc(ProductInfoEntity::getInsertTime);
        return super.page(req, wrapper, ProductInfoEntityRespVO.class);
    }

}