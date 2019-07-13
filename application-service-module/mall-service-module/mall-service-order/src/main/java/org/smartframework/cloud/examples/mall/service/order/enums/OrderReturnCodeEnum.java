package org.smartframework.cloud.examples.mall.service.order.enums;

import org.smartframework.cloud.common.pojo.enums.IBaseReturnCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单服务状态码
 *
 * @author liyulin
 * @date 2019-04-16
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderReturnCodeEnum implements IBaseReturnCode {

	/** 库存更新失败 */
	UPDATE_STOCK_FAIL("201101", "库存更新失败"),
	/** 商品不存在 */
	PRODUCT_NOT_EXIST("201102", "商品不存在");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String message;

}