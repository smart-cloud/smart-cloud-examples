package org.smartframework.cloud.examples.mall.order.biz.api;

import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 运单信息api biz
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Repository
public class OrderDeliveryInfoApiBiz extends BaseBiz<OrderDeliveryInfoEntity> {

    @Autowired
    private OrderDeliveryInfoBaseMapper orderDeliveryInfoBaseMapper;

}