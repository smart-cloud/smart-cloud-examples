package org.smartframework.cloud.examples.mall.service.order.mapper.base;

import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.base.OrderDeliveryInfoBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 运单信息base mapper
 *
 * @author liyulin
 * @date 2019-07-15
 */
public interface OrderDeliveryInfoBaseMapper extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoBaseRespBody, Long> {

}