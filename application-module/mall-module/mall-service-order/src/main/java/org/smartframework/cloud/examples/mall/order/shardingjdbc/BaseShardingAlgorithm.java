package org.smartframework.cloud.examples.mall.order.shardingjdbc;

/**
 * @author collin
 * @date 2021-02-09
 */
public abstract class BaseShardingAlgorithm {

    protected static final String SHARDING_COLUMN_UID = "f_buyer";

    protected static final String SHARDING_COLUMN_ORDER_NO = "f_order_no";

}