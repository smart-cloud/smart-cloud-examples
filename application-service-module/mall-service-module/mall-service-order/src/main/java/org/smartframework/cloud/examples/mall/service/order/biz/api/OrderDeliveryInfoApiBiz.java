package org.smartframework.cloud.examples.mall.service.order.biz.api;

import java.util.List;

import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.service.order.mapper.base.OrderDeliveryInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 运单信息api biz
 *
 * @author liyulin
 * @date 2019年4月8日上午12:53:39
 */
@Repository
public class OrderDeliveryInfoApiBiz extends BaseBiz<OrderDeliveryInfoEntity> {

	@Autowired
	private OrderDeliveryInfoBaseMapper orderDeliveryInfoBaseMapper;

	public boolean create(List<OrderDeliveryInfoEntity> entities) {
		return orderDeliveryInfoBaseMapper.insertList(entities) > 0;
	}

}