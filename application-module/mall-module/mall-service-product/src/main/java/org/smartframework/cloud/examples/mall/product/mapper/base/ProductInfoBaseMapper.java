package org.smartframework.cloud.examples.mall.product.mapper.base;

import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 商品信息base mapper
 *
 * @author liyulin
 * @date 2019-11-09
 */
public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoBaseRespVO, Long> {

}