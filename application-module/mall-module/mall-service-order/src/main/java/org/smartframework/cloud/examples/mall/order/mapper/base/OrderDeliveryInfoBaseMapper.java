package org.smartframework.cloud.examples.mall.order.mapper.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;

/**
 * 运单信息base mapper
 *
 * @author liyulin
 * @date 2019-11-09
 */
@DS(DataSourceName.MALL_ORDER)
@Mapper
public interface OrderDeliveryInfoBaseMapper extends BaseMapper<OrderDeliveryInfoEntity> {

}