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
package org.smartframework.cloud.examples.mall.order.mq.producer;

import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.mall.order.mq.OrderMqConstants;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.starter.rabbitmq.AbstractRabbitMqProducer;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer extends AbstractRabbitMqProducer {

    /**
     * 发送【提交订单】mq
     *
     * @param req
     */
    public String submitOrder(SubmitOrderReqVO req) {
        SubmitOrderDTO submitOrderDTO = new SubmitOrderDTO();
        Long userId = UserContext.getUserId();
        submitOrderDTO.setUserId(userId);
        String orderNo = OrderUtil.generateOrderNo(userId);
        submitOrderDTO.setOrderNo(orderNo);
        submitOrderDTO.setSubmtOrderProductInfos(req.getProducts());

        // 发送mq
        super.send(OrderMqConstants.SubmitOrder.EXCHANGE, OrderMqConstants.SubmitOrder.ROUTEKEY, submitOrderDTO);
        return orderNo;
    }

}