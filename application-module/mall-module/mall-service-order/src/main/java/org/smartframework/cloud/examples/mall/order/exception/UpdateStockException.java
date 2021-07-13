package org.smartframework.cloud.examples.mall.order.exception;

import org.smartframework.cloud.examples.mall.order.enums.OrderReturnCodes;
import org.smartframework.cloud.exception.BaseException;

/**
 * 库存更新异常
 *
 * @author liyulin
 * @date 2019-04-16
 */
public class UpdateStockException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UpdateStockException() {
		super(OrderReturnCodes.UPDATE_STOCK_FAIL);
	}

}