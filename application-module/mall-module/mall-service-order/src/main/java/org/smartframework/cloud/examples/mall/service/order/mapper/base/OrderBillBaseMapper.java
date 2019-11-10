package org.smartframework.cloud.examples.mall.service.order.mapper.base;

import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.base.OrderBillBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 订单信息base mapper
 *
 * @author liyulin
 * @date 2019-11-09
 */
public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillBaseRespBody, Long> {

}