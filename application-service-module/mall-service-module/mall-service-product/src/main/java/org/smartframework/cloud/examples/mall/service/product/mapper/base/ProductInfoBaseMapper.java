package org.smartframework.cloud.examples.mall.service.product.mapper.base;

import org.smartframework.cloud.examples.mall.service.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.base.ProductInfoBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoBaseRespBody, Long> {

}