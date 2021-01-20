package org.smartframework.cloud.examples.mall.order.controller.api;

import org.smartframework.cloud.api.core.annotation.SmartApiAcess;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.service.api.OrderApiService;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    private OrderApiService orderApiService;

    /**
     * 创建订单
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @SmartApiAcess(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true, auth = true, repeatSubmitCheck = true)
    public RespVO<CreateOrderRespVO> create(@RequestBody @Valid CreateOrderReqVO req) {
        return RespUtil.success(orderApiService.create(req));
    }

}