package org.smartframework.cloud.examples.mall.order.mapper.base;

import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.rpc.order.response.base.OrderDeliveryInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 运单信息base mapper
 *
 * @author liyulin
 * @date 2019-11-09
 */
public interface OrderDeliveryInfoBaseMapper extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoBaseRespVO, Long> {

}