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
package org.smartframework.cloud.examples.mall.product.biz.oms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.starter.global.id.GlobalId;
import io.github.smart.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.mall.product.entity.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 商品信息oms biz
 *
 * @author collin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoOmsBiz extends BaseBiz<ProductInfoBaseMapper, ProductInfoEntity> {

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
    public BasePageResponse<ProductInfoBaseRespVO> pageProduct(PageProductReqVO req) {
        LambdaQueryWrapper<ProductInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(ProductInfoEntity::getName, name);
        }
        wrapper.eq(ProductInfoEntity::getDelState, DeleteState.NORMAL);
        wrapper.orderByDesc(ProductInfoEntity::getInsertTime);
        return super.page(req, wrapper, ProductInfoBaseRespVO.class);
    }

}