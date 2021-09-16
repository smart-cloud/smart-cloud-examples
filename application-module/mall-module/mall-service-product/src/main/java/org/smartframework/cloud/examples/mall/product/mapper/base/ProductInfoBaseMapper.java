package org.smartframework.cloud.examples.mall.product.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 商品信息base mapper
 *
 * @author liyulin
 * @date 2021-03-23
 */
@Mapper
public interface ProductInfoBaseMapper extends SmartMapper<ProductInfoEntity> {

}