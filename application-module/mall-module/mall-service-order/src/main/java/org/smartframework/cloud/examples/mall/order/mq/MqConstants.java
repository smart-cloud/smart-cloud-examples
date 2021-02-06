package org.smartframework.cloud.examples.mall.order.mq;

/**
 * mq常量
 *
 * @author liyulin
 * @date 2021-02-06
 */
public interface MqConstants {

    interface Queue {
        /**
         * 提交订单
         */
        String SUBMIT_ORDER = "submitOrderQueue";
    }

    interface Exchange {
        /**
         * 提交授信订单
         */
        String SUBMIT_ORDER = "submitOrderExchange";
    }

    interface RouteKey {
        /**
         * 提交授信订单
         */
        String SUBMIT_ORDER = "submitOrderRouteKey";
    }

}