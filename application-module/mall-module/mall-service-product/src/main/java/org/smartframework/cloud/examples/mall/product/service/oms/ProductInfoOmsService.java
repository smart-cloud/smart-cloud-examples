package org.smartframework.cloud.examples.mall.product.service.oms;

import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.mall.product.biz.oms.ProductInfoOmsBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息 oms service
 *
 * @author liyulin
 * @date 2019-03-29
 */
@Service
public class ProductInfoOmsService {

    @Autowired
    private ProductInfoOmsBiz productOmsBiz;

    /**
     * 新增
     *
     * @param reqBody
     * @return
     */
    public Boolean create(ProductInsertReqVO reqBody) {
        return productOmsBiz.insert(reqBody);
    }

    /**
     * 修改
     *
     * @param reqBody
     * @return
     */
    public Boolean update(ProductUpdateReqVO reqBody) {
        return productOmsBiz.update(reqBody);
    }

    /**
     * 逻辑删除
     *
     * @param reqBody
     * @return
     */
    public Boolean logicDelete(ProductDeleteReqVO reqBody) {
        return productOmsBiz.logicDelete(reqBody.getId());
    }

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<ProductInfoBaseRespVO> pageProduct(PageProductReqVO req) {
        return productOmsBiz.pageProduct(req);
    }

}