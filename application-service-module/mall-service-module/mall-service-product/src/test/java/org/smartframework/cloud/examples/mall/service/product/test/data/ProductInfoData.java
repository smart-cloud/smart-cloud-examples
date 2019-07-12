package org.smartframework.cloud.examples.mall.service.product.test.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.smartframework.cloud.examples.mall.service.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.service.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoData {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	/**
	 * 插入指定id的数据
	 * 
	 * @param id
	 */
	public void insertTestData(Long id) {
		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setId(id);
		entity.setName("iphone");
		entity.setSellPrice(1000L);
		entity.setStock(2000L);
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		productInfoBaseMapper.insertSelective(entity);
	}

	/**
	 * 批量插入数据
	 */
	public void batchInsertTestData() {
		List<Long> list = new ArrayList<>();
		for (long id = 100; id < 111; id++) {
			list.add(id);
		}
		
		batchInsertTestData(list);
	}

	/**
	 * 批量插入数据
	 */
	public void batchInsertTestData(List<Long> ids) {
		List<ProductInfoEntity> list = new ArrayList<>();
		for (Long id : ids) {
			ProductInfoEntity entity = new ProductInfoEntity();
			entity.setId(id);
			entity.setName("iphone");
			entity.setSellPrice(1000L);
			entity.setStock(2000L);
			entity.setAddTime(new Date());
			entity.setDelState(DelStateEnum.NORMAL.getDelState());

			list.add(entity);
		}
		productInfoBaseMapper.insertList(list);
	}

}