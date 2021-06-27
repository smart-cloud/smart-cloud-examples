package org.smartframework.cloud.examples.mall.product.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
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
public class ProductInfoOmsBiz extends BaseBiz<ProductInfoEntity> {

    /**
     * 新增
     *
     * @param reqBody
     * @return
     */
    public boolean insert(ProductInsertReqVO reqBody) {
        ProductInfoEntity record = create();
        record.setName(reqBody.getName());
        record.setSellPrice(reqBody.getSellPrice());
        record.setStock(reqBody.getStock());
        record.setInsertUser(UserContext.getUserId());
        return super.save(record);
    }

    /**
     * 修改
     *
     * @param reqBody
     * @return
     */
    public boolean update(ProductUpdateReqVO reqBody) {
        ProductInfoEntity record = new ProductInfoEntity();
        record.setId(reqBody.getId());
        record.setName(reqBody.getName());
        record.setSellPrice(reqBody.getSellPrice());
        record.setStock(reqBody.getStock());
        record.setUpdTime(new Date());
        record.setUpdUser(UserContext.getUserId());
        return super.updateById(record);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public boolean logicDelete(long id) {
        ProductInfoEntity record = new ProductInfoEntity();
        record.setId(id);
        record.setDelState(DelStateEnum.DELETED.getDelState());
        record.setDelTime(new Date());
        record.setDelUser(UserContext.getUserId());
        return super.updateById(record);
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
        wrapper.eq(BaseEntity::getDelState, DelStateEnum.NORMAL.getDelState());
        wrapper.orderByDesc(BaseEntity::getInsertTime);
        return super.page(req, wrapper, ProductInfoBaseRespVO.class);
    }

}