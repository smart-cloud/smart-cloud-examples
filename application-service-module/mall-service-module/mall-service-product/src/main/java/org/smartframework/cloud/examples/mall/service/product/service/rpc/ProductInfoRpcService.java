package org.smartframework.cloud.examples.mall.service.product.service.rpc;

import java.util.List;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.product.biz.rpc.ProductInfoRpcBiz;
import org.smartframework.cloud.examples.mall.service.product.enums.ProductReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdsReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdsRespBody;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
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
	public QryProductByIdRespBody qryProductById(QryProductByIdReqBody reqBody) {
		return productRpcBiz.qryProductById(reqBody);
	}
	
	/**
	 * 根据ids查询商品信息
	 * 
	 * @param reqBody
	 * @return
	 */
	public QryProductByIdsRespBody qryProductByIds(QryProductByIdsReqBody reqBody) {
		return productRpcBiz.qryProductByIds(reqBody);
	}

	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	@Transactional
	public Resp<BaseDto> updateStock(List<UpdateStockReqBody> list) {
		boolean success = productRpcBiz.updateStock(list);
		
		return success ? RespUtil.success() : RespUtil.error(ProductReturnCodeEnum.STOCK_NOT_ENOUGH);
	}

}