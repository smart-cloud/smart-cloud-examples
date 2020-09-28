package org.smartframework.cloud.examples.mall.product.biz.rpc;

import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.product.mapper.rpc.ProductInfoRpcMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.utility.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息rpc biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoRpcBiz extends BaseBiz<ProductInfoEntity> {

    @Autowired
    private ProductInfoBaseMapper productInfoBaseMapper;
    @Autowired
    private ProductInfoRpcMapper productInfoRpcMapper;

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    public QryProductByIdRespVO qryProductById(QryProductByIdReqVO reqBody) {
        ProductInfoEntity entity = productInfoBaseMapper.selectById(reqBody.getId());
        if (ObjectUtil.isNull(entity)) {
            return null;
        }

        return QryProductByIdRespVO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellPrice(entity.getSellPrice())
                .stock(entity.getStock())
                .build();
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqVO
     * @return
     */
    public QryProductByIdsRespVO qryProductByIds(QryProductByIdsReqVO reqVO) {
        List<ProductInfoEntity> entities = productInfoBaseMapper.selectBatchIds(reqVO.getIds());
        if (ObjectUtil.isNull(entities)) {
            return null;
        }

        List<QryProductByIdRespVO> productInfos = entities.stream()
                .map(entity -> QryProductByIdRespVO.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .sellPrice(entity.getSellPrice())
                        .stock(entity.getStock())
                        .build())
                .collect(Collectors.toList());

        return new QryProductByIdsRespVO(productInfos);
    }

    /**
     * 扣减库存
     *
     * @param list
     * @return
     */
    public boolean updateStock(List<UpdateStockItem> list) {
        return productInfoRpcMapper.updateStock(list) > 0;
    }

}