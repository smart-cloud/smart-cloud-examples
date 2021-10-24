package org.smartframework.cloud.examples.mall.order.shardingjdbc;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;

import java.util.Collection;

/**
 * 分表算法
 *
 * @author collin
 * @date 2021-02-09
 */
public class OrderDeliveryInfoTableShardingAlgorithm<T extends Comparable<?>> extends BaseShardingAlgorithm implements StandardShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        return availableTargetNames;
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String orderNo = shardingValue.getValue();
        Long orderNoSharding = OrderUtil.whichTable(orderNo);
        return String.format("%s_%s", shardingValue.getLogicTableName(), orderNoSharding);
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return ShardingAlgorithmsType.ORDER_DELIVERY_INFO_TABLE_TYPE;
    }

}