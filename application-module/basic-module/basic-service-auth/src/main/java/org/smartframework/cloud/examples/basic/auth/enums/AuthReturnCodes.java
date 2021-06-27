package org.smartframework.cloud.examples.basic.auth.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

/**
 * 权限服务状态码
 *
 * @author liyulin
 * @date 2019-04-16
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthReturnCodes implements IBaseReturnCodes {

    /**
     * 权限编码已存在
     */
    PERMISSION_CODE_EXIST("110001"),
    /**
     * 角色编码已存在
     */
    ROLE_CODE_EXIST("110002");

    /**
     * 状态码
     */
    private String code;

}