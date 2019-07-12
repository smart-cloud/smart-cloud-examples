package org.smartframework.cloud.examples.mall.service.product.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.product.service.api.ProductInfoApiService;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.api.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.api.PageProductRespBody;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/identity/product/productInfo")
@Validated
@Api(tags = "商品信息api相关接口")
public class ProductInfoApiController {

	@Autowired
	private ProductInfoApiService productService;

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	public Resp<BasePageResp<PageProductRespBody>> pageProduct(
			@RequestBody @Valid BasePageReq<PageProductReqBody> req) {
		return RespUtil.success(productService.pageProduct(req));
	}

}