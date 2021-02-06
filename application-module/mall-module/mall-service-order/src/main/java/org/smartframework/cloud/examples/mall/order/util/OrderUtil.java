package org.smartframework.cloud.examples.mall.order.util;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.utility.DateUtil;

/**
 * 订单工具类
 *
 * @author collin
 * @date 2021-02-08
 */
public class OrderUtil {

    /**
     * 每个库的数据量
     */
    private static final long DB_DATA_LIMIT = 1_0000_0000L;
    /**
     * 每个库的表数量
     */
    private static final long DB_TABLE_NUM = 100L;

    /**
     * 生成订单号
     *
     * @param uid
     * @return
     */
    public static String generateOrderNo(Long uid) {
        // yyyyMMddHHmmssSSS+uid/100000000+uid%100
        return new StringBuilder()
                .append(DateUtil.getCurrentDateTime("yyyyMMddHHmmssSSS"))
                .append(StringUtils.leftPad(String.valueOf(uid / DB_DATA_LIMIT), 8, '0'))
                .append(StringUtils.leftPad(String.valueOf(uid % DB_TABLE_NUM), 2, '0')).toString();
    }

}