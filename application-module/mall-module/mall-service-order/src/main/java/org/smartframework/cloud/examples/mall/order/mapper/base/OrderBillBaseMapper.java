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
package org.smartframework.cloud.examples.mall.order.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 订单信息base mapper
 *
 * @author collin
 * @date 2021-12-12
 */
@Mapper
public interface OrderBillBaseMapper extends SmartMapper<OrderBillEntity> {

}