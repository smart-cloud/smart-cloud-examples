package org.smartframework.cloud.examples.mall.rpc.product;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@SmartFeignClient(name = RpcConstants.Product.FEIGN_CLIENT_NAME)
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("根据id查询商品信息")
	@PostMapping(value="product/rpc/productInfo/qryProductById")
	RespVO<QryProductByIdRespVO> qryProductById(@RequestBody @Valid QryProductByIdReqVO req);

	@ApiOperation("根据ids查询商品信息")
	@PostMapping(value="product/rpc/productInfo/qryProductByIds")
	RespVO<QryProductByIdsRespVO> qryProductByIds(@RequestBody @Valid QryProductByIdsReqVO req);

	@ApiOperation("更新库存")
	@PostMapping(value="product/rpc/productInfo/updateStock")
	RespVO<Base> updateStock(@RequestBody @Valid UpdateStockReqVO req);

}