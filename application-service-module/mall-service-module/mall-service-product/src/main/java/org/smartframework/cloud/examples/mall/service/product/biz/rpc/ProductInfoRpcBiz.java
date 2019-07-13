package org.smartframework.cloud.examples.mall.service.product.biz.rpc;

import java.util.List;
import java.util.stream.Collectors;

import org.smartframework.cloud.examples.mall.service.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.service.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.service.product.mapper.rpc.ProductInfoRpcMapper;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdsReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdsRespBody;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.utility.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public QryProductByIdRespBody qryProductById(QryProductByIdReqBody reqBody) {
		ProductInfoEntity entity = productInfoBaseMapper.selectByPrimaryKey(reqBody.getId());
		if (ObjectUtil.isNull(entity)) {
			return null;
		}

		return QryProductByIdRespBody.builder()
				.id(entity.getId())
				.name(entity.getName())
				.sellPrice(entity.getSellPrice())
				.stock(entity.getStock())
				.build();
	}
	
	/**
	 * 根据ids查询商品信息
	 * 
	 * @param reqBody
	 * @return
	 */
	public QryProductByIdsRespBody qryProductByIds(QryProductByIdsReqBody reqBody) {
		List<ProductInfoEntity> entities = productInfoBaseMapper.selectByIdList(reqBody.getIds());
		if (ObjectUtil.isNull(entities)) {
			return null;
		}

		List<QryProductByIdRespBody> productInfos = entities.stream().map(entity->{
			return QryProductByIdRespBody.builder()
					.id(entity.getId())
					.name(entity.getName())
					.sellPrice(entity.getSellPrice())
					.stock(entity.getStock())
					.build();
		}).collect(Collectors.toList());
		
		return new QryProductByIdsRespBody(productInfos);
	}
	
	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	public boolean updateStock(List<UpdateStockReqBody> list) {
		return productInfoRpcMapper.updateStock(list)>0;
	}

}