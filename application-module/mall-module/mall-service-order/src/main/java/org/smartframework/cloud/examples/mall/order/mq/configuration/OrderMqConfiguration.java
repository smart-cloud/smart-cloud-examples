package org.smartframework.cloud.examples.mall.order.mq.configuration;

import org.smartframework.cloud.examples.mall.order.mq.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMqConfiguration {

    @Bean
    public Queue submitOrderQueue() {
        return new Queue(MqConstants.Queue.SUBMIT_ORDER);
    }

    @Bean
    public DirectExchange submitOrderExchange() {
        return new DirectExchange(MqConstants.Exchange.SUBMIT_ORDER);
    }

    @Bean
    public Binding bindingCreditOrder(final Queue submitOrderQueue,
                                      final DirectExchange submitOrderExchange) {
        return BindingBuilder
                .bind(submitOrderQueue)
                .to(submitOrderExchange)
                .with(MqConstants.RouteKey.SUBMIT_ORDER);
    }

}