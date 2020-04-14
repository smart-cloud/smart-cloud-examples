package org.smartframework.cloud.examples.mall.product.controller.api;

import javax.validation.Valid;

import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.vo.BasePageReqVO;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.service.api.ProductInfoApiService;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
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
@RequestMapping("product/api/productInfo")
@Validated
@Api(tags = "商品信息api相关接口")
public class ProductInfoApiController {

	@Autowired
	private ProductInfoApiService productService;

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	@SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
	public RespVO<BasePageRespVO<PageProductRespVO>> pageProduct(
			@RequestBody @Valid BasePageReqVO<PageProductReqVO> req) {
		return RespUtil.success(productService.pageProduct(req));
	}

}