package org.smartframework.cloud.examples.mall.product.service.oms;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.product.biz.oms.ProductInfoOmsBiz;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
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
	public Response<Base> create(ProductInsertReqVO reqBody) {
		productOmsBiz.insert(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 修改
	 * 
	 * @param reqBody
	 * @return
	 */
	public Response<Base> update(ProductUpdateReqVO reqBody) {
		productOmsBiz.update(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 逻辑删除
	 * 
	 * @param reqBody
	 * @return
	 */
	public Response<Base> logicDelete(ProductDeleteReqVO reqBody) {
		productOmsBiz.logicDelete(reqBody.getId());
		return RespUtil.success();
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