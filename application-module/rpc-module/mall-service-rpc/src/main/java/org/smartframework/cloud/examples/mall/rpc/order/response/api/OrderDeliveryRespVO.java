package org.smartframework.cloud.examples.mall.rpc.order.response.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 运单信息
 *
 * @author collin
 * @date 2021-02-07
 */

@Getter
@Setter
public class OrderDeliveryRespVO extends Base {

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品购买价格（单位：万分之一元）
     */
    private Long price;
    /**
     * 购买数量
     */
    private int buyCount;

}