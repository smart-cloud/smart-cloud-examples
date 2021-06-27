package org.smartframework.cloud.examples.mall.order.mq;

import org.smartframework.cloud.starter.rabbitmq.MQConstants;

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
        String QUEUE = PREFIX + MQConstants.QUEUE_SUFFIX;
        /**
         * 提交授信订单
         */
        String EXCHANGE = PREFIX + MQConstants.EXCHANGE_SUFFIX;
        /**
         * 提交授信订单
         */
        String ROUTEKEY = PREFIX + MQConstants.ROUTEKEY_SUFFIX;
    }

}