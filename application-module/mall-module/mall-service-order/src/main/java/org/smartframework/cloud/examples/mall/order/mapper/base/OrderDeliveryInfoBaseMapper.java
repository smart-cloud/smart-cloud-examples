package org.smartframework.cloud.examples.mall.order.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.SmartMapper;

/**
 * 运单信息base mapper
 *
 * @author liyulin
 * @date 2021-03-23
 */
@Mapper
public interface OrderDeliveryInfoBaseMapper extends SmartMapper<OrderDeliveryInfoEntity> {

}