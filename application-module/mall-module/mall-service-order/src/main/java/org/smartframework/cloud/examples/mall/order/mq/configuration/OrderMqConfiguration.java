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
package org.smartframework.cloud.examples.mall.order.mq.configuration;

import org.smartframework.cloud.examples.mall.order.mq.OrderMqConstants;
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
        return new Queue(OrderMqConstants.SubmitOrder.QUEUE);
    }

    @Bean
    public DirectExchange submitOrderExchange() {
        return new DirectExchange(OrderMqConstants.SubmitOrder.EXCHANGE);
    }

    @Bean
    public Binding bindingCreditOrder(final Queue submitOrderQueue,
                                      final DirectExchange submitOrderExchange) {
        return BindingBuilder
                .bind(submitOrderQueue)
                .to(submitOrderExchange)
                .with(OrderMqConstants.SubmitOrder.ROUTEKEY);
    }

}