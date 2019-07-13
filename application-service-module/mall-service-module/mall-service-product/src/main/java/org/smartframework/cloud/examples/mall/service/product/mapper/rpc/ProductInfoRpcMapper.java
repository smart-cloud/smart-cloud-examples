package org.smartframework.cloud.examples.mall.service.product.mapper.rpc;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;

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
	int updateStock(@Param("list") List<UpdateStockReqBody> list);

}