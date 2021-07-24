package org.smartframework.cloud.examples.mall.product.biz.rpc;

import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.product.mapper.rpc.ProductInfoRpcMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
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
public class ProductInfoRpcBiz extends BaseBiz<ProductInfoBaseMapper, ProductInfoEntity> {

    @Autowired
    private ProductInfoRpcMapper productInfoRpcMapper;

    /**
     * 根据id查询商品信息
     *
     * @param reqBody
     * @return
     */
    public QryProductByIdRespDTO qryProductById(QryProductByIdReqDTO reqBody) {
        ProductInfoEntity entity = super.getById(reqBody.getId());
        if (ObjectUtil.isNull(entity)) {
            return null;
        }

        return QryProductByIdRespDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellPrice(entity.getSellPrice())
                .stock(entity.getStock())
                .build();
    }

    /**
     * 根据ids查询商品信息
     *
     * @param reqDTO
     * @return
     */
    public QryProductByIdsRespDTO qryProductByIds(QryProductByIdsReqDTO reqDTO) {
        List<ProductInfoEntity> entities = super.listByIds(reqDTO.getIds());
        if (ObjectUtil.isNull(entities)) {
            return null;
        }

        List<QryProductByIdRespDTO> productInfos = entities.stream()
                .map(entity -> QryProductByIdRespDTO.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .sellPrice(entity.getSellPrice())
                        .stock(entity.getStock())
                        .build())
                .collect(Collectors.toList());

        return new QryProductByIdsRespDTO(productInfos);
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