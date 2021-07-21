package org.smartframework.cloud.examples.mall.product.service.rpc;

import org.smartframework.cloud.examples.mall.product.biz.rpc.ProductInfoRpcBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品信息rpc service
 *
 * @author liyulin
 * @date 2019-03-29
 */
@Service
public class ProductInfoRpcService {

    @Autowired
    private ProductInfoRpcBiz productRpcBiz;

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    public QryProductByIdRespDTO qryProductById(QryProductByIdReqDTO reqBody) {
        return productRpcBiz.qryProductById(reqBody);
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqVO
     * @return
     */
    public QryProductByIdsRespDTO qryProductByIds(QryProductByIdsReqDTO reqVO) {
        return productRpcBiz.qryProductByIds(reqVO);
    }

    /**
     * 扣减库存
     *
     * @param req
     * @return
     */
    @Transactional
    public Boolean updateStock(UpdateStockReqDTO req) {
        return productRpcBiz.updateStock(req.getItems());
    }

}