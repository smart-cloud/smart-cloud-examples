package org.smartframework.cloud.examples.mall.order.mq;

import org.smartframework.cloud.starter.rabbitmq.MqConstants;

/**
 * mq常量
 *
 * @author liyulin
 * @date 2021-02-06
 */
public interface OrderMqConstants {

    interface SubmitOrder {
        String PREFIX = "submitOrder";
        /**
         * 提交订单
         */
        String QUEUE = PREFIX + MqConstants.QUEUE_SUFFIX;
        /**
         * 提交授信订单
         */
        String EXCHANGE = PREFIX + MqConstants.EXCHANGE_SUFFIX;
        /**
         * 提交授信订单
         */
        String ROUTEKEY = PREFIX + MqConstants.ROUTEKEY_SUFFIX;
    }

}