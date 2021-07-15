package org.smartframework.cloud.examples.support.gateway.exception;

import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.exception.BaseException;

/**
 * 无权限访问
 *
 * @author collin
 * @date 2021-07-15
 */
public class AuthenticationException extends BaseException {

    public AuthenticationException() {
        super(CommonReturnCodes.NO_ACCESS);
    }

}