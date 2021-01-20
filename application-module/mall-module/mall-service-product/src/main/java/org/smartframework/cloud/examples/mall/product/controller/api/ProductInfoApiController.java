package org.smartframework.cloud.examples.mall.product.controller.api;

import org.smartframework.cloud.api.core.annotation.SmartApiAcess;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.service.api.ProductInfoApiService;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品信息
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("product/api/productInfo")
@Validated
public class ProductInfoApiController {

    @Autowired
    private ProductInfoApiService productService;

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    @GetMapping("pageProduct")
    @SmartApiAcess(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<BasePageRespVO<PageProductRespVO>> pageProduct(@Valid @NotNull PageProductReqVO req) {
        return RespUtil.success(productService.pageProduct(req));
    }

}