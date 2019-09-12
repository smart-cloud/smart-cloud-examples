package org.smartframework.cloud.examples.mall.service.product.service.api;

import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.examples.mall.service.product.biz.api.ProductInfoApiBiz;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.api.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.api.PageProductRespBody;
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
	public BasePageResp<PageProductRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productOmsBiz.pageProduct(req);
	}
	
}