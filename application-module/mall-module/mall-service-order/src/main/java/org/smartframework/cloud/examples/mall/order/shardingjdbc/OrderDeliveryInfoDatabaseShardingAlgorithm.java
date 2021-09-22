package org.smartframework.cloud.examples.mall.order.shardingjdbc;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;

import java.util.Collection;

/**
 * 分库算法
 *
 * @author collin
 * @date 2021-02-09
 */
public class OrderDeliveryInfoDatabaseShardingAlgorithm<T extends Comparable<?>> extends BaseShardingAlgorithm implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableDataBaseName, PreciseShardingValue<T> preciseShardingValue) {
        String orderNo = (String) preciseShardingValue.getValue();
        Long orderNoSharding = OrderUtil.whichDB(orderNo);

        return getDataBaseName(availableDataBaseName, orderNoSharding);
    }

    private String getDataBaseName(Collection<String> databaseNames, Long idSharding) {
        return databaseNames.stream().filter(x -> x.endsWith(String.valueOf(idSharding))).findFirst().orElse(null);
    }

}