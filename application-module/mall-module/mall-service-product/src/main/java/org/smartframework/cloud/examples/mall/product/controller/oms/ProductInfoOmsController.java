package org.smartframework.cloud.examples.mall.product.controller.oms;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.BasePageReqVO;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.service.oms.ProductInfoOmsService;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("product/oms/productInfo")
@Validated
@Api(tags = "商品信息oms相关接口")
public class ProductInfoOmsController {

	@Autowired
	private ProductInfoOmsService productOmsService;

	@ApiOperation("新增商品信息")
	@PostMapping("create")
	public RespVO<Base> create(@RequestBody @Valid ProductInsertReqVO req) {
		return productOmsService.create(req);
	}

	@ApiOperation("修改商品信息")
	@PostMapping("update")
	public RespVO<Base> update(@RequestBody @Valid ProductUpdateReqVO req) {
		return productOmsService.update(req);
	}

	@ApiOperation("逻辑删除商品")
	@PostMapping("logicDelete")
	public RespVO<Base> logicDelete(@RequestBody @Valid ProductDeleteReqVO req) {
		return productOmsService.logicDelete(req);
	}

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	public RespVO<BasePageRespVO<ProductInfoBaseRespVO>> pageProduct(
			@RequestBody @Valid BasePageReqVO<PageProductReqVO> req) {
		return RespUtil.success(productOmsService.pageProduct(req));
	}

}