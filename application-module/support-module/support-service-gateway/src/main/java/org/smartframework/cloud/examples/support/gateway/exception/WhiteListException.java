package org.smartframework.cloud.examples.support.gateway.exception;

import io.github.smart.cloud.exception.BaseException;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;

/**
 * 白名单异常
 *
 * @author collin
 * @date 2024-03-26
 */
public class WhiteListException extends BaseException {

    public WhiteListException() {
        super(GatewayReturnCodes.NOT_IN_WHITE_LIST);
    }

}