package org.smartframework.cloud.examples.mall.product.controller.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.product.enums.ProductReturnCodes;
import org.smartframework.cloud.examples.mall.product.service.rpc.ProductInfoRpcService;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ProductInfoRpcController implements ProductInfoRpc {

    @Autowired
    private ProductInfoRpcService productInfoRpcService;

    @Override
    public Response<QryProductByIdRespDTO> qryProductById(QryProductByIdReqDTO req) {
        return RespUtil.success(productInfoRpcService.qryProductById(req));
    }

    @Override
    public Response<QryProductByIdsRespDTO> qryProductByIds(QryProductByIdsReqDTO reqVO) {
        return RespUtil.success(productInfoRpcService.qryProductByIds(reqVO));
    }

    @Override
    public Response<Base> updateStock(UpdateStockReqDTO req) {
        boolean success = productInfoRpcService.updateStock(req);
        return success ? RespUtil.success() : RespUtil.error(ProductReturnCodes.STOCK_NOT_ENOUGH);
    }

}