package org.smartframework.cloud.examples.mall.order.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

/**
 * 订单服务状态码
 *
 * @author liyulin
 * @date 2019-04-16
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderReturnCodes implements IBaseReturnCodes {

    /**
     * 库存更新失败
     */
    UPDATE_STOCK_FAIL("200001"),
    /**
     * 商品不存在
     */
    PRODUCT_NOT_EXIST("200002");

    /**
     * 状态码
     */
    private String code;

}