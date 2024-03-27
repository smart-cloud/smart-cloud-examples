package org.smartframework.cloud.examples.support.gateway.exception;

import io.github.smart.cloud.exception.BaseException;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;

/**
 * 黑名单异常
 *
 * @author collin
 * @date 2024-03-26
 */
public class BlackListException extends BaseException {

    public BlackListException() {
        super(GatewayReturnCodes.BLACK_LIST_FORBIDDEN_ACCSS);
    }

}