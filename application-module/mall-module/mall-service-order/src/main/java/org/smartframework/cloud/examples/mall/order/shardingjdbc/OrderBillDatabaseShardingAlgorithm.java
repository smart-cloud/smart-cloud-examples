package org.smartframework.cloud.examples.mall.order.shardingjdbc;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.smartframework.cloud.examples.mall.order.util.OrderUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分库算法
 *
 * @author collin
 * @date 2021-02-09
 */
public class OrderBillDatabaseShardingAlgorithm<T extends Comparable<?>> extends BaseShardingAlgorithm implements ComplexKeysShardingAlgorithm<T> {

    @Override
    public Collection<String> doSharding(Collection<String> availableDataBaseName, ComplexKeysShardingValue<T> complexKeysShardingValue) {
        Set<String> targetDbNames = new HashSet<>();

        Map<String, Collection<T>> columnNameAndShardingValueMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        Collection<T> uidValues = columnNameAndShardingValueMap.get(SHARDING_COLUMN_UID);
        if (uidValues != null) {
            uidValues.stream().forEach(uid -> {
                Long uidSharding = OrderUtil.whichDB((Long) uid);
                targetDbNames.addAll(getDataBaseNames(availableDataBaseName, uidSharding));
            });
        }

        Collection<T> orderNoValues = columnNameAndShardingValueMap.get(SHARDING_COLUMN_ORDER_NO);
        if (orderNoValues != null) {
            orderNoValues.stream().forEach(orderNo -> {
                Long orderNoSharding = OrderUtil.whichDB((String) orderNo);
                targetDbNames.addAll(getDataBaseNames(availableDataBaseName, orderNoSharding));
            });
        }

        return targetDbNames;
    }

    private Collection<String> getDataBaseNames(Collection<String> databaseNames, Long idSharding) {
        return databaseNames.stream().filter(x -> x.endsWith("-" + idSharding)).collect(Collectors.toSet());
    }

}