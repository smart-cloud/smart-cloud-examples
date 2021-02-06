package org.smartframework.cloud.examples.mall.order.mq.consumer;

import org.smartframework.cloud.examples.mall.order.mq.MqConstants;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.starter.rabbitmq.AbstractRabbitConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MqConstants.Queue.SUBMIT_ORDER)
public class OrderConsumer extends AbstractRabbitConsumer<SubmitOrderDTO> {

    @Autowired
    private OrderApiService orderApiService;

    @Override
    protected void doProcess(SubmitOrderDTO submitOrderDTO) {
        orderApiService.submit(submitOrderDTO);
    }

}