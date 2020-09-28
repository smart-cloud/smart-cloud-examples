package org.smartframework.cloud.examples.mall.order.service.api;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liyulin
 * @date 2020-09-28
 */
@Service
public class OrderDeliveryInfoApiService extends ServiceImpl<OrderDeliveryInfoBaseMapper, OrderDeliveryInfoEntity> {

    public boolean create(List<OrderDeliveryInfoEntity> entities) {
        return super.saveBatch(entities);
    }

}