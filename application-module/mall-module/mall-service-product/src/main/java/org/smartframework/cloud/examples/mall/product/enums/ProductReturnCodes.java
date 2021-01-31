package org.smartframework.cloud.examples.mall.product.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

/**
 * 商品服务状态码
 *
 * @author liyulin
 * @date 2019-04-07
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProductReturnCodes implements IBaseReturnCodes {

    /**
     * 库存不足，操作失败
     */
    STOCK_NOT_ENOUGH("300001");

    /**
     * 状态码
     */
    private String code;

}