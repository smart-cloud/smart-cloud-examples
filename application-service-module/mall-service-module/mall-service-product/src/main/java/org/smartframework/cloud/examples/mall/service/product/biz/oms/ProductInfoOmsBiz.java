package org.smartframework.cloud.examples.mall.service.product.biz.oms;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.examples.mall.service.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.service.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductInsertReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.oms.ProductUpdateReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.base.ProductInfoBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 商品信息oms biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoOmsBiz extends BaseBiz<ProductInfoEntity> {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	/**
	 * 新增
	 * 
	 * @param reqBody
	 * @return
	 */
	public boolean insert(ProductInsertReqBody reqBody) {
		ProductInfoEntity record = create();
		record.setName(reqBody.getName());
		record.setSellPrice(reqBody.getSellPrice());
		record.setStock(reqBody.getStock());
		
		return productInfoBaseMapper.insertSelective(record) > 0;
	}
	
	/**
	 * 修改
	 * 
	 * @param reqBody
	 * @return
	 */
	public boolean update(ProductUpdateReqBody reqBody) {
		ProductInfoEntity record = new ProductInfoEntity();
		record.setId(reqBody.getId());
		record.setName(reqBody.getName());
		record.setSellPrice(reqBody.getSellPrice());
		record.setStock(reqBody.getStock());
		record.setUpdTime(new Date());
		
		return productInfoBaseMapper.updateByPrimaryKeySelective(record) > 0;
	}
	
	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean logicDelete(long id) {
		return productInfoBaseMapper.logicDeleteByPrimaryKey(id, null, new Date())>0;
	}
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<ProductInfoBaseRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		PageProductReqBody reqBody = req.getQuery();
		if (!Objects.isNull(reqBody) && StringUtils.isNotBlank(reqBody.getName())) {
			criteria.andLike(ProductInfoEntity.Columns.NAME.getProperty(), reqBody.getName() + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.NORMAL.getDelState());
		example.orderBy(BaseEntity.Columns.ADD_TIME.getProperty()).desc();

		return productInfoBaseMapper.pageRespByExample(example, req.getPageNum(), req.getPageSize());
	}

}