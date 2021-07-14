package org.smartframework.cloud.examples.app.auth.core.exception;

import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.exception.BaseException;

/**
 * 用户信息获取不到异常
 *
 * @author liyulin
 * @date 2020-09-10
 */
public class SmartUserMissingException extends BaseException {

    public SmartUserMissingException() {
        super(CommonReturnCodes.NOT_LOGGED_IN);
    }

}