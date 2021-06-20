package org.smartframework.cloud.examples.mall.product.controller.oms;

import org.smartframework.cloud.api.core.annotation.SmartRequiresDataSecurity;
import org.smartframework.cloud.api.core.annotation.SmartRequiresRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.auth.SmartRequiresPermissions;
import org.smartframework.cloud.common.pojo.Base;
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
    @SmartRequiresPermissions("product:productInfo:create")
    @SmartRequiresDataSecurity
    @SmartRequiresRepeatSubmitCheck
    public Response<Base> create(@RequestBody @Valid ProductInsertReqVO req) {
        return productOmsService.create(req);
    }

    /**
     * 修改商品信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @SmartRequiresPermissions("product:productInfo:update")
    @SmartRequiresDataSecurity
    public Response<Base> update(@RequestBody @Valid ProductUpdateReqVO req) {
        return productOmsService.update(req);
    }

    /**
     * 逻辑删除商品
     *
     * @param req
     * @return
     */
    @PostMapping("logicDelete")
    @SmartRequiresPermissions("product:productInfo:logicDelete")
    @SmartRequiresDataSecurity
    public Response<Base> logicDelete(@RequestBody @Valid ProductDeleteReqVO req) {
        return productOmsService.logicDelete(req);
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