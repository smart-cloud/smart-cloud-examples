package org.smartframework.cloud.examples.mall.order.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.constants.ShardingJdbcDS;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运单信息api biz
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Repository
@DS(ShardingJdbcDS.MASTER)
public class OrderDeliveryInfoApiBiz extends BaseBiz<OrderDeliveryInfoBaseMapper, OrderDeliveryInfoEntity> {

    /**
     * 根据订单号查询运单信息
     *
     * @param orderNo 订单号
     * @return
     */
    @DS(ShardingJdbcDS.SLAVE)
    public List<OrderDeliveryInfoEntity> getByOrderNo(String orderNo) {
        return super.list(new LambdaQueryWrapper<OrderDeliveryInfoEntity>().eq(OrderDeliveryInfoEntity::getOrderNo, orderNo));
    }

}