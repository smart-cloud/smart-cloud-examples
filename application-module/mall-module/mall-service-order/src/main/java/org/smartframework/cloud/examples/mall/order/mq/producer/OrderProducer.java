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