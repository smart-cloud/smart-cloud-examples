package org.smartframework.cloud.examples.mall.order.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderBillBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.enums.order.OrderStatus;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.smartframework.cloud.starter.mybatis.constants.ShardingJdbcDS;
import org.springframework.stereotype.Repository;

/**
 * 订单信息api biz
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Repository
@DS(ShardingJdbcDS.MASTER)
public class OrderBillApiBiz extends BaseBiz<OrderBillBaseMapper, OrderBillEntity> {

    public long create(OrderBillEntity entity) {
        super.save(entity);
        return entity.getId();
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNo 订单号
     * @return
     */
    @DS(ShardingJdbcDS.SLAVE)
    public OrderBillEntity getByOrderNo(String orderNo) {
        return super.getOne(new LambdaQueryWrapper<OrderBillEntity>()
                .eq(OrderBillEntity::getOrderNo, orderNo)
                .eq(OrderBillEntity::getDelState, DelState.NORMAL));
    }

    /**
     * 更新订单状态
     *
     * @param orderNo
     * @param status
     */
    public void updateStatus(String orderNo, OrderStatus status) {
        OrderBillEntity entity = new OrderBillEntity();
        entity.setStatus(status.getStatus());
        super.update(entity, new LambdaQueryWrapper<OrderBillEntity>().eq(OrderBillEntity::getOrderNo, orderNo));
    }

}