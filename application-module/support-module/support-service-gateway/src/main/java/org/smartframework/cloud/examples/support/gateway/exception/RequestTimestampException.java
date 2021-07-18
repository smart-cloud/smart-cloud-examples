package org.smartframework.cloud.examples.support.gateway.exception;

import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.exception.BaseException;

/**
 * 请求时间异常
 *
 * @author collin
 * @date 2021-07-17
 */
public class RequestTimestampException extends BaseException {

    public RequestTimestampException(GatewayReturnCodes returnCodes) {
        super(returnCodes);
    }

}