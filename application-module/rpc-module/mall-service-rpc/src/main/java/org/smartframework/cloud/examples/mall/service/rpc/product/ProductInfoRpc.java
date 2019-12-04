package org.smartframework.cloud.examples.mall.service.rpc.product;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdsReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdsRespBody;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@SmartFeignClient(name = RpcConstants.Product.FEIGN_CLIENT_NAME)
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("根据id查询商品信息")
	@PostMapping("rpc/identity/product/productInfo/qryProductById")
	Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid QryProductByIdReqBody req);

	@ApiOperation("根据ids查询商品信息")
	@PostMapping("rpc/identity/product/productInfo/qryProductByIds")
	Resp<QryProductByIdsRespBody> qryProductByIds(@RequestBody @Valid QryProductByIdsReqBody req);

	@ApiOperation("更新库存")
	@PostMapping("rpc/identity/product/productInfo/updateStock")
	Resp<BaseDto> updateStock(@RequestBody @Valid UpdateStockReqBody req);

}