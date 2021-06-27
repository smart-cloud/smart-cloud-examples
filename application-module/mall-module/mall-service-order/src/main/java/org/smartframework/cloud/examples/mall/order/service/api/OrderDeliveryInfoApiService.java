package org.smartframework.cloud.examples.mall.order.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderDeliveryInfoApiBiz;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.starter.mybatis.constants.ShardingJdbcDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运单
 *
 * @author liyulin
 * @date 2020-09-28
 */
@Service
@DS(ShardingJdbcDS.MASTER)
public class OrderDeliveryInfoApiService {

    @Autowired
    private OrderDeliveryInfoApiBiz orderDeliveryInfoApiBiz;

    public boolean create(List<OrderDeliveryInfoEntity> entities) {
        return orderDeliveryInfoApiBiz.getBaseMapper().insertBatchSomeColumn(entities) == entities.size();
    }

}