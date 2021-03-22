package org.smartframework.cloud.examples.mall.order.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.constants.ShardingJdbcDS;
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
public class OrderDeliveryInfoApiService extends ServiceImpl<OrderDeliveryInfoBaseMapper, OrderDeliveryInfoEntity> {

    public boolean create(List<OrderDeliveryInfoEntity> entities) {
        return getBaseMapper().insertBatchSomeColumn(entities) == entities.size();
    }

}