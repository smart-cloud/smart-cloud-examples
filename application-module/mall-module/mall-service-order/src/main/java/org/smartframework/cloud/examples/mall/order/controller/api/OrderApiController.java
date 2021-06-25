package org.smartframework.cloud.examples.mall.order.controller.api;

import org.smartframework.cloud.api.core.annotation.SmartRequiresDataSecurity;
import org.smartframework.cloud.api.core.annotation.SmartRequiresRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.auth.SmartRequiresUser;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.mq.producer.OrderProducer;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 订单
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("order/api/order")
@Validated
public class OrderApiController {

    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private OrderApiService orderApiService;

    /**
     * 提交订单
     *
     * @param req
     * @return
     */
    @PostMapping("submit")
    @SmartRequiresUser
    @SmartRequiresDataSecurity
    @SmartRequiresRepeatSubmitCheck
    public RespVO<String> submit(@RequestBody @Valid SubmitOrderReqVO req) {
        return RespUtil.success(orderProducer.submitOrder(req));
    }

    /**
     * 查询提交结果
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("querySubmitResult")
    public RespVO<QuerySubmitResultRespVO> querySubmitResult(@NotNull String orderNo) {
        return RespUtil.success(orderApiService.querySubmitResult(orderNo));
    }

}