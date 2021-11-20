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
package org.smartframework.cloud.examples.mall.order.controller.api;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.api.core.annotation.auth.RequireUser;
import org.smartframework.cloud.common.pojo.Response;
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
 * @author collin
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
    @RequireUser
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<String> submit(@RequestBody @Valid SubmitOrderReqVO req) {
        return RespUtil.success(orderProducer.submitOrder(req));
    }

    /**
     * 查询提交结果
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("querySubmitResult")
    @RequireUser
    @RequireTimestamp
    public Response<QuerySubmitResultRespVO> querySubmitResult(@NotNull String orderNo) {
        return RespUtil.success(orderApiService.querySubmitResult(orderNo));
    }

}