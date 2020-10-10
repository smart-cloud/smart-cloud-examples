package org.smartframework.cloud.examples.mall.product.biz.oms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息oms biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoOmsBiz extends BaseBiz<ProductInfoEntity> {

    @Autowired
    private ProductInfoBaseMapper productInfoBaseMapper;

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
        record.setAddUser(UserContext.getUserId());

        return productInfoBaseMapper.insert(record) > 0;
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

        return productInfoBaseMapper.updateById(record) > 0;
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
        return productInfoBaseMapper.updateById(record) > 0;
    }

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    public BasePageRespVO<ProductInfoBaseRespVO> pageProduct(PageProductReqVO req) {
        LambdaQueryWrapper<ProductInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(ProductInfoEntity::getName, name);
        }
        wrapper.eq(BaseEntity::getDelState, DelStateEnum.NORMAL.getDelState());
        wrapper.orderByDesc(BaseEntity::getAddTime);

        IPage<ProductInfoEntity> page = productInfoBaseMapper.selectPage(new Page<>(req.getPageNum(), req.getPageSize(), true), wrapper);
        List<ProductInfoEntity> entitydatas = page.getRecords();

        if (CollectionUtils.isEmpty(entitydatas)) {
            return new BasePageRespVO<>(null, req.getPageNum(), req.getPageSize(), 0);
        }

        List<ProductInfoBaseRespVO> pagedatas = entitydatas.stream()
                .map(entity -> ProductInfoBaseRespVO.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .sellPrice(entity.getSellPrice())
                        .stock(entity.getStock())
                        .build())
                .collect(Collectors.toList());

        return new BasePageRespVO<>(pagedatas, req.getPageNum(), req.getPageSize(), page.getTotal());
    }

}