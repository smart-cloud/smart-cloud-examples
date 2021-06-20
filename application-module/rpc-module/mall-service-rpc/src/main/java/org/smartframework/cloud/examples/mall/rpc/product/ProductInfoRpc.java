package org.smartframework.cloud.examples.mall.rpc.product;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品信息rpc相关接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Product.FEIGN_CLIENT_NAME, contextId = "productInfoRpc")
public interface ProductInfoRpc {

    /**
     * 根据id查询商品信息
     *
     * @param req
     * @return
     */
    @GetMapping("product/rpc/productInfo/qryProductById")
    Response<QryProductByIdRespVO> qryProductById(@SpringQueryMap @Valid @NotNull QryProductByIdReqVO req);

    /**
     * 根据ids查询商品信息
     *
     * @param reqVO
     * @return
     */
    @GetMapping("product/rpc/productInfo/qryProductByIds")
    Response<QryProductByIdsRespVO> qryProductByIds(@SpringQueryMap @Valid @NotNull QryProductByIdsReqVO reqVO);

    /**
     * 更新库存
     *
     * @param req
     * @return
     */
    @PostMapping("product/rpc/productInfo/updateStock")
    Response<Base> updateStock(@RequestBody @Valid UpdateStockReqVO req);

}