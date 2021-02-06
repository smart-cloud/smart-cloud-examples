package org.smartframework.cloud.examples.mall.order.biz.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据订单号查询运单信息
     *
     * @param orderNo 订单号
     * @return
     */
    public List<OrderDeliveryInfoEntity> getByOrderNo(String orderNo) {
        return orderDeliveryInfoBaseMapper.selectList(new LambdaQueryWrapper<OrderDeliveryInfoEntity>().eq(OrderDeliveryInfoEntity::getOrderNo, orderNo));
    }

}