package org.smartframework.cloud.examples.mall.product.controller.rpc;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.product.enums.ProductReturnCodes;
import org.smartframework.cloud.examples.mall.product.service.rpc.ProductInfoRpcService;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
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
    public Response<QryProductByIdRespVO> qryProductById(QryProductByIdReqVO req) {
        return RespUtil.success(productInfoRpcService.qryProductById(req));
    }

    @Override
    public Response<QryProductByIdsRespVO> qryProductByIds(QryProductByIdsReqVO reqVO) {
        return RespUtil.success(productInfoRpcService.qryProductByIds(reqVO));
    }

    @Override
    public Response<Base> updateStock(UpdateStockReqVO req) {
        boolean success = productInfoRpcService.updateStock(req);
        return success ? RespUtil.success() : RespUtil.error(ProductReturnCodes.STOCK_NOT_ENOUGH);
    }

}