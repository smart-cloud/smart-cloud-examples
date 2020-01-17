package org.smartframework.cloud.examples.mall.product.service.api;

import org.smartframework.cloud.common.pojo.vo.BasePageReqVO;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.examples.mall.product.biz.api.ProductInfoApiBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息 api service
 *
 * @author liyulin
 * @date 2019-03-29
 */
@Service
public class ProductInfoApiService {
	
	@Autowired
	private ProductInfoApiBiz productOmsBiz;

	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageRespVO<PageProductRespVO> pageProduct(BasePageReqVO<PageProductReqVO> req) {
		return productOmsBiz.pageProduct(req);
	}
	
}