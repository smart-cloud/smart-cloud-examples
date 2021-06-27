package org.smartframework.cloud.examples.mall.order.mq.consumer;

import org.smartframework.cloud.examples.mall.order.mq.OrderMqConstants;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.starter.rabbitmq.AbstractRabbitMQConsumer;
import org.smartframework.cloud.starter.rabbitmq.annotation.MQConsumerFailRetry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MQConsumerFailRetry
@RabbitListener(queues = OrderMqConstants.SubmitOrder.QUEUE)
public class OrderConsumer extends AbstractRabbitMQConsumer<SubmitOrderDTO> {

    @Autowired
    private OrderApiService orderApiService;

    @Override
    protected void doProcess(SubmitOrderDTO submitOrderDTO) {
        orderApiService.submit(submitOrderDTO);
    }

}