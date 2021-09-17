package org.smartframework.cloud.examples.mall.product.service.rpc;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.product.biz.rpc.ProductInfoRpcBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息rpc service
 *
 * @author liyulin
 * @date 2019-03-29
 */
@DS(DataSourceName.MALL_PRODUCT)
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
     * @param reqDTO
     * @return
     */
    public QryProductByIdsRespDTO qryProductByIds(QryProductByIdsReqDTO reqDTO) {
        return productRpcBiz.qryProductByIds(reqDTO);
    }

    /**
     * 扣减库存
     *
     * @param req
     * @return
     */
    @DSTransactional
    public Boolean updateStock(UpdateStockReqDTO req) {
        return productRpcBiz.updateStock(req.getItems());
    }

}