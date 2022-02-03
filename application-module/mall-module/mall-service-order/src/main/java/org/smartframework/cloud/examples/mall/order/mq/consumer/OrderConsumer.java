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
package org.smartframework.cloud.examples.mall.order.mq.consumer;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.mall.order.mq.OrderMqConstants;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.starter.rabbitmq.AbstractRabbitMqConsumer;
import org.smartframework.cloud.starter.rabbitmq.annotation.MqConsumerFailRetry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@MqConsumerFailRetry
@RequiredArgsConstructor
@RabbitListener(queues = OrderMqConstants.SubmitOrder.QUEUE)
public class OrderConsumer extends AbstractRabbitMqConsumer<SubmitOrderDTO> {

    private final OrderApiService orderApiService;

    @Override
    protected void doProcess(SubmitOrderDTO submitOrderDTO) {
        orderApiService.submit(submitOrderDTO);
    }

}