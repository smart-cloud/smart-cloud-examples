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
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.mapper.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mp.shardingjdbc.constants.ShardingSphereDataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运单信息api biz
 *
 * @author collin
 * @date 2019-04-08
 */
@Repository
@DS(ShardingSphereDataSourceName.SHARDING_DATASOURCE)
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