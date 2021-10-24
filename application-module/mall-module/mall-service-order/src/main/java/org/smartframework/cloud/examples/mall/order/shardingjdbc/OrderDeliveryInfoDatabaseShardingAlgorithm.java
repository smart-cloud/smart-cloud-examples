package org.smartframework.cloud.examples.mall.order.shardingjdbc;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;

import java.util.Collection;

/**
 * 分库算法
 *
 * @author collin
 * @date 2021-02-09
 */
public class OrderDeliveryInfoDatabaseShardingAlgorithm extends BaseShardingAlgorithm implements StandardShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String orderNo = shardingValue.getValue();
        Long orderNoSharding = OrderUtil.whichDB(orderNo);

        return getDataBaseName(availableTargetNames, orderNoSharding);
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        return availableTargetNames;
    }

    private String getDataBaseName(Collection<String> databaseNames, Long idSharding) {
        return databaseNames.stream().filter(x -> x.endsWith(String.valueOf(idSharding))).findFirst().orElse(null);
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return ShardingAlgorithmsType.ORDER_DELIVERY_INFO_DATABASE_TYPE;
    }

}