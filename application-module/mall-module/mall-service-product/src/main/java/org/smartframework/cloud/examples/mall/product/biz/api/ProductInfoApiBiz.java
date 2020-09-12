package org.smartframework.cloud.examples.mall.product.biz.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息api biz
 *
 * @author liyulin
 * @date 2019-03-31
 */
@Repository
public class ProductInfoApiBiz extends BaseBiz<ProductInfoEntity> {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageRespVO<PageProductRespVO> pageProduct(PageProductReqVO req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		String name = req.getName();
		if (StringUtils.isNotBlank(name)) {
			criteria.andLike(ProductInfoEntity.Columns.name.toString(), "%" + name + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.delState.toString(), DelStateEnum.NORMAL.getDelState());
		example.orderBy(BaseEntity.Columns.addTime.toString()).desc();
		
		Page<ProductInfoEntity> page = PageMethod.startPage(req.getPageNum(), req.getPageSize(), true);
		List<ProductInfoEntity> entitydatas = productInfoBaseMapper.selectByExample(example);
		
		if (CollectionUtils.isEmpty(entitydatas)) {
			return new BasePageRespVO<>(null, req.getPageNum(), req.getPageSize(), 0);
		}
		
		List<PageProductRespVO> pagedatas = entitydatas.stream()
				.map(entity -> PageProductRespVO.builder()
						.id(entity.getId())
						.name(entity.getName())
						.sellPrice(entity.getSellPrice())
						.stock(entity.getStock())
						.build())
				.collect(Collectors.toList());

		return new BasePageRespVO<>(pagedatas, req.getPageNum(), req.getPageSize(), page.getTotal());
	}

}