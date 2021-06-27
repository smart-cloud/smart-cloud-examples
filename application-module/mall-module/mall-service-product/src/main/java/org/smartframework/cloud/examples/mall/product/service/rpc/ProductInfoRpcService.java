package org.smartframework.cloud.examples.mall.product.service.rpc;

import org.smartframework.cloud.examples.mall.product.biz.rpc.ProductInfoRpcBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
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
    public QryProductByIdRespVO qryProductById(QryProductByIdReqVO reqBody) {
        return productRpcBiz.qryProductById(reqBody);
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqVO
     * @return
     */
    public QryProductByIdsRespVO qryProductByIds(QryProductByIdsReqVO reqVO) {
        return productRpcBiz.qryProductByIds(reqVO);
    }

    /**
     * 扣减库存
     *
     * @param req
     * @return
     */
    @Transactional
    public Boolean updateStock(UpdateStockReqVO req) {
        return productRpcBiz.updateStock(req.getItems());
    }

}