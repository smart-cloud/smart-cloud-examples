package org.smartframework.cloud.examples.app.auth.core.exception;

import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.starter.core.business.exception.BaseException;

/**
 * @author liyulin
 * @date 2020-09-10
 */
public class UserBOMissingException extends BaseException {

    public UserBOMissingException() {
        super(CommonReturnCodes.NOT_LOGGED_IN);
    }

}