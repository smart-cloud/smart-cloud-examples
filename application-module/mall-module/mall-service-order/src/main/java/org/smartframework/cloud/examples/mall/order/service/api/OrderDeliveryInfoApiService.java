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
package org.smartframework.cloud.examples.mall.order.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderDeliveryInfoApiBiz;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.starter.mp.shardingjdbc.constants.ShardingSphereDataSourceName;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运单
 *
 * @author collin
 * @date 2020-09-28
 */
@Service
@DS(ShardingSphereDataSourceName.SHARDING_DATASOURCE)
@RequiredArgsConstructor
public class OrderDeliveryInfoApiService {

    private final OrderDeliveryInfoApiBiz orderDeliveryInfoApiBiz;

    public boolean create(List<OrderDeliveryInfoEntity> entities) {
        return orderDeliveryInfoApiBiz.getBaseMapper().insertBatchSomeColumn(entities) == entities.size();
    }

}