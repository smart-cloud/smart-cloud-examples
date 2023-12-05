package org.smartframework.cloud.examples.support.admin.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务线代码
 *
 * @author collin
 * @date 2023-12-05
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RobotTemplateCode {

    /**
     * 风控
     */
    RISK("risk"),
    /**
     * 业务
     */
    BUSINESS("business");

    private String code;

}