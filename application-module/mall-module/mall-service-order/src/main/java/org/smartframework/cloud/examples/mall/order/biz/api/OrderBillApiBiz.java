/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.mall.order.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.order.mapper.base.OrderBillBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.enums.order.OrderStatus;
import org.smartframework.cloud.starter.mp.shardingjdbc.constants.ShardingSphereDataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.plus.enums.DeleteState;
import org.springframework.stereotype.Repository;

/**
 * 订单信息api biz
 *
 * @author collin
 * @date 2019-04-08
 */
@Repository
@DS(ShardingSphereDataSourceName.SHARDING_DATASOURCE)
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
    public OrderBillEntity getByOrderNo(String orderNo) {
        return super.getOne(new LambdaQueryWrapper<OrderBillEntity>()
                .eq(OrderBillEntity::getOrderNo, orderNo)
                .eq(OrderBillEntity::getDelState, DeleteState.NORMAL));
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