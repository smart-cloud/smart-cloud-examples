package org.smartframework.cloud.examples.support.gateway.exception;

import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.exception.BaseException;

/**
 * 请求参数签名校验失败
 *
 * @author collin
 * @date 2021-07-25
 */
public class RequestSignFailException extends BaseException {

    public RequestSignFailException() {
        super(GatewayReturnCodes.REQUEST_SIGN_CHECK_FAIL);
    }

}