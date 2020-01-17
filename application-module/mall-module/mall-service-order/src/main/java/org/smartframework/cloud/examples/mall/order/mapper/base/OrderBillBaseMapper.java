package org.smartframework.cloud.examples.mall.order.mapper.base;

import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.rpc.order.response.base.OrderBillBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 订单信息base mapper
 *
 * @author liyulin
 * @date 2019-11-09
 */
public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillBaseRespVO, Long> {

}