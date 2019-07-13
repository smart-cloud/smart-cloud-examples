package org.smartframework.cloud.examples.mall.service.product.service.oms;

import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.product.biz.oms.ProductInfoOmsBiz;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductDeleteReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductInsertReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductUpdateReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.base.ProductInfoBaseRespBody;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
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
	public Resp<BaseDto> create(ProductInsertReqBody reqBody) {
		productOmsBiz.insert(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 修改
	 * 
	 * @param reqBody
	 * @return
	 */
	public Resp<BaseDto> update(ProductUpdateReqBody reqBody) {
		productOmsBiz.update(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 逻辑删除
	 * 
	 * @param reqBody
	 * @return
	 */
	public Resp<BaseDto> logicDelete(ProductDeleteReqBody reqBody) {
		productOmsBiz.logicDelete(reqBody.getId());
		return RespUtil.success();
	}
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<ProductInfoBaseRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productOmsBiz.pageProduct(req);
	}
	
}