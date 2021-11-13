package org.smartframework.cloud.examples.mall.order.mq.consumer;

import org.smartframework.cloud.examples.mall.order.mq.OrderMqConstants;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.starter.rabbitmq.AbstractRabbitMqConsumer;
import org.smartframework.cloud.starter.rabbitmq.annotation.MqConsumerFailRetry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MqConsumerFailRetry
@RabbitListener(queues = OrderMqConstants.SubmitOrder.QUEUE)
public class OrderConsumer extends AbstractRabbitMqConsumer<SubmitOrderDTO> {

    @Autowired
    private OrderApiService orderApiService;

    @Override
    protected void doProcess(SubmitOrderDTO submitOrderDTO) {
        orderApiService.submit(submitOrderDTO);
    }

}