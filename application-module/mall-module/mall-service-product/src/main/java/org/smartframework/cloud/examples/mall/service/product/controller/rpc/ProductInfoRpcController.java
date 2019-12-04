package org.smartframework.cloud.examples.mall.service.product.controller.rpc;

import javax.validation.Valid;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.product.service.rpc.ProductInfoRpcService;
import org.smartframework.cloud.examples.mall.service.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdsReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdsRespBody;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ProductInfoRpcController implements ProductInfoRpc {

	@Autowired
	private ProductInfoRpcService productInfoRpcService;

	@Override
	public Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid QryProductByIdReqBody req) {
		return RespUtil.success(productInfoRpcService.qryProductById(req));
	}

	@Override
	public Resp<QryProductByIdsRespBody> qryProductByIds(@RequestBody @Valid QryProductByIdsReqBody req) {
		return RespUtil.success(productInfoRpcService.qryProductByIds(req));
	}

	@Override
	public Resp<BaseDto> updateStock(@RequestBody @Valid UpdateStockReqBody req) {
		return productInfoRpcService.updateStock(req);
	}

}