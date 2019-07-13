package org.smartframework.cloud.examples.mall.service.product.biz.api;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.dto.BasePageReq;
import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.examples.mall.service.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.service.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.api.PageProductReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.api.PageProductRespBody;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.smartframework.cloud.utility.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
	public BasePageResp<PageProductRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		PageProductReqBody reqBody = req.getQuery();
		if (!Objects.isNull(reqBody) && StringUtils.isNotBlank(reqBody.getName())) {
			criteria.andLike(ProductInfoEntity.Columns.NAME.getProperty(), "%" + reqBody.getName() + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.NORMAL.getDelState());
		example.orderBy(BaseEntity.Columns.ADD_TIME.getProperty()).desc();

		Page<ProductInfoEntity> page = PageHelper.startPage(req.getPageNum(), req.getPageSize(), true);
		List<ProductInfoEntity> entitydatas = productInfoBaseMapper.selectByExample(example);
		
		if (CollectionUtil.isEmpty(entitydatas)) {
			return new BasePageResp<>(null, req.getPageNum(), req.getPageSize(), 0);
		}
		
		List<PageProductRespBody> pagedatas = entitydatas.stream().map(entity->{
			PageProductRespBody pagedata = PageProductRespBody.builder()
					.id(entity.getId())
					.name(entity.getName())
					.sellPrice(entity.getSellPrice())
					.stock(entity.getStock())
					.build();
			return pagedata;
		}).collect(Collectors.toList());

		return new BasePageResp<>(pagedatas, req.getPageNum(), req.getPageSize(), page.getTotal());
	}

}