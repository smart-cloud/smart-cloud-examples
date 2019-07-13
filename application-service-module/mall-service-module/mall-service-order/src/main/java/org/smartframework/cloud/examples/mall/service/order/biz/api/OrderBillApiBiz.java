package org.smartframework.cloud.examples.mall.service.order.biz.api;

import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.service.order.mapper.base.OrderBillBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 订单信息api biz
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Repository
public class OrderBillApiBiz extends BaseBiz<OrderBillEntity> {

	@Autowired
	private OrderBillBaseMapper orderBillBaseMapper;

	public long create(OrderBillEntity entity) {
		orderBillBaseMapper.insertSelective(entity);
		return entity.getId();
	}

}