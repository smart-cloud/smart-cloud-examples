package org.smartframework.cloud.examples.mall.product.mapper.rpc;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO.UpdateStockItem;

/**
 * 商品rpc mapper
 *
 * @author liyulin
 * @date 2019-04-07
 */
public interface ProductInfoRpcMapper {

	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	int updateStock(@Param("list") List<UpdateStockItem> list);

}