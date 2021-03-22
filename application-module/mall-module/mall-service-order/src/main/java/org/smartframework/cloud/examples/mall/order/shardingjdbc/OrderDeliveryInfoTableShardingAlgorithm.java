package org.smartframework.cloud.examples.mall.order.shardingjdbc;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;

import java.util.Collection;

/**
 * 分表算法
 *
 * @author collin
 * @date 2021-02-09
 */
public class OrderDeliveryInfoTableShardingAlgorithm<T extends Comparable<?>> extends BaseShardingAlgorithm implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTables, PreciseShardingValue<T> preciseShardingValue) {
        String orderNo = (String) preciseShardingValue.getValue();
        Long orderNoSharding = OrderUtil.whichTable(orderNo);
        return getTableName(availableTables, orderNoSharding);
    }

    private String getTableName(Collection<String> availableTables, Long idSharding) {
        return availableTables.stream().filter(x -> x.endsWith("_" + idSharding)).findFirst().orElse(null);
    }

}