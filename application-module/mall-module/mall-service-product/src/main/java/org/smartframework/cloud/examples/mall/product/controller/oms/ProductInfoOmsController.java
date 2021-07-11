package org.smartframework.cloud.examples.mall.product.controller.oms;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
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
 * @author liyulin
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
    public Response<BasePageResponse<ProductInfoBaseRespVO>> pageProduct(@Valid @NotNull PageProductReqVO req) {
        return RespUtil.success(productOmsService.pageProduct(req));
    }

}