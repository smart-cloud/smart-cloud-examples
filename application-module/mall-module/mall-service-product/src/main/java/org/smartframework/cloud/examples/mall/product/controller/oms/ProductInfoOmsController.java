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
package org.smartframework.cloud.examples.mall.product.controller.oms;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.product.service.oms.ProductInfoOmsService;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品信息管理
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("product/oms/productInfo")
@Validated
public class ProductInfoOmsController {

    @Autowired
    private ProductInfoOmsService productOmsService;

    /**
     * 新增商品信息
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequirePermissions("product:productInfo:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<Boolean> create(@RequestBody @Valid ProductInsertReqVO req) {
        return RespUtil.success(productOmsService.create(req));
    }

    /**
     * 修改商品信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequirePermissions("product:productInfo:update")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> update(@RequestBody @Valid ProductUpdateReqVO req) {
        return RespUtil.success(productOmsService.update(req));
    }

    /**
     * 逻辑删除商品
     *
     * @param req
     * @return
     */
    @PostMapping("logicDelete")
    @RequirePermissions("product:productInfo:logicDelete")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> logicDelete(@RequestBody @Valid ProductDeleteReqVO req) {
        return RespUtil.success(productOmsService.logicDelete(req));
    }

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    @GetMapping("pageProduct")
    @RequireTimestamp
    public Response<BasePageResponse<ProductInfoBaseRespVO>> pageProduct(@Valid @NotNull PageProductReqVO req) {
        return RespUtil.success(productOmsService.pageProduct(req));
    }

}