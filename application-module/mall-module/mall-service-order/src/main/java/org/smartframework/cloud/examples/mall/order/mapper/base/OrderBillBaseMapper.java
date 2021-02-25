package org.smartframework.cloud.examples.mall.order.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;

/**
 * 订单信息base mapper
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Mapper
public interface OrderBillBaseMapper extends BaseMapper<OrderBillEntity> {

}