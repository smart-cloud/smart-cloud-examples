package org.smartframework.cloud.examples.mall.service.order.exception;

import org.smartframework.cloud.examples.mall.service.order.enums.OrderReturnCodeEnum;
import org.smartframework.cloud.starter.common.business.exception.BaseException;

/**
 * 库存更新异常
 *
 * @author liyulin
 * @date 2019年4月16日下午4:49:18
 */
public class UpdateStockException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UpdateStockException() {
		super(OrderReturnCodeEnum.UPDATE_STOCK_FAIL);
	}

}