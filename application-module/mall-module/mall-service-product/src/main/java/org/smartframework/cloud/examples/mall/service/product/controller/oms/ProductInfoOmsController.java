package org.smartframework.cloud.examples.mall.service.product.controller.oms;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.product.service.oms.ProductInfoOmsService;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductDeleteReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductInsertReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductUpdateReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.base.ProductInfoBaseRespBody;
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
@RequestMapping("oms/auth/product/productInfo")
@Validated
@Api(tags = "商品信息oms相关接口")
public class ProductInfoOmsController {

	@Autowired
	private ProductInfoOmsService productOmsService;

	@ApiOperation("新增商品信息")
	@PostMapping("create")
	public Resp<BaseDto> create(@RequestBody @Valid ProductInsertReqBody req) {
		return productOmsService.create(req);
	}

	@ApiOperation("修改商品信息")
	@PostMapping("update")
	public Resp<BaseDto> update(@RequestBody @Valid ProductUpdateReqBody req) {
		return productOmsService.update(req);
	}

	@ApiOperation("逻辑删除商品")
	@PostMapping("logicDelete")
	public Resp<BaseDto> logicDelete(@RequestBody @Valid ProductDeleteReqBody req) {
		return productOmsService.logicDelete(req);
	}

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	public Resp<BasePageResp<ProductInfoBaseRespBody>> pageProduct(
			@RequestBody @Valid BasePageReq<PageProductReqBody> req) {
		return RespUtil.success(productOmsService.pageProduct(req));
	}

}