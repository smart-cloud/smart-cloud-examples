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
package org.smartframework.cloud.examples.mall.product.biz.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.mall.product.entity.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息api biz
 *
 * @author collin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoApiBiz extends BaseBiz<ProductInfoBaseMapper, ProductInfoEntity> {

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<PageProductRespVO> pageProduct(PageProductReqVO req) {
        LambdaQueryWrapper<ProductInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(ProductInfoEntity::getName, name);
        }
        wrapper.eq(ProductInfoEntity::getDelState, DeleteState.NORMAL);
        wrapper.orderByDesc(ProductInfoEntity::getInsertTime);
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

}