package org.smartframework.cloud.examples.mall.product.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 商品信息oms biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
@DS(DataSourceName.MALL_PRODUCT)
public class ProductInfoOmsBiz extends BaseBiz<ProductInfoBaseMapper, ProductInfoEntity> {

    /**
     * 新增
     *
     * @param reqBody
     * @return
     */
    public boolean insert(ProductInsertReqVO reqBody) {
        ProductInfoEntity productInfoEntity = create();
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
        wrapper.eq(ProductInfoEntity::getDelState, DelState.NORMAL);
        wrapper.orderByDesc(ProductInfoEntity::getInsertTime);
        return super.page(req, wrapper, ProductInfoBaseRespVO.class);
    }

}