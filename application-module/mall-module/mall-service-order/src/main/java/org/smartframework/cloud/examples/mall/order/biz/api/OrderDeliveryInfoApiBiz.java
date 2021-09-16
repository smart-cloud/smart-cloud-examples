package org.smartframework.cloud.examples.mall.order.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mp.shardingjdbc.constants.ShardingjdbcDatasourceNames;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运单信息api biz
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Repository
@DS(ShardingjdbcDatasourceNames.SHARDING_DATASOURCE)
public class OrderDeliveryInfoApiBiz extends BaseBiz<OrderDeliveryInfoBaseMapper, OrderDeliveryInfoEntity> {

    /**
     * 根据订单号查询运单信息
     *
     * @param orderNo 订单号
     * @return
     */
    public List<OrderDeliveryInfoEntity> getByOrderNo(String orderNo) {
        return super.list(new LambdaQueryWrapper<OrderDeliveryInfoEntity>().eq(OrderDeliveryInfoEntity::getOrderNo, orderNo));
    }

}