package org.smartframework.cloud.examples.mall.product.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息api biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
@DS(DataSourceName.MALL_PRODUCT)
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
        wrapper.eq(ProductInfoEntity::getDelState, DelState.NORMAL);
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