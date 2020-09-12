package org.smartframework.cloud.examples.mall.product.biz.oms;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;

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
	public boolean insert(ProductInsertReqVO reqBody) {
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
	public boolean update(ProductUpdateReqVO reqBody) {
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
	public BasePageRespVO<ProductInfoBaseRespVO> pageProduct(PageProductReqVO req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		String name = req.getName();
		if (StringUtils.isNotBlank(name)) {
			criteria.andLike(ProductInfoEntity.Columns.name.toString(), name + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.delState.toString(), DelStateEnum.NORMAL.getDelState());
		example.orderBy(BaseEntity.Columns.addTime.toString()).desc();

		return productInfoBaseMapper.pageRespByExample(example, req.getPageNum(), req.getPageSize());
	}

}