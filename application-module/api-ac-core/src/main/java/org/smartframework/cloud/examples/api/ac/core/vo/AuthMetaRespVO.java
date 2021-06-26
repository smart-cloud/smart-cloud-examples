package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 接口鉴权meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthMetaRespVO extends Base {

    /**
     * 是否需要登陆校验（false则不需要校验）
     */
    private boolean requiresUser;

    /**
     * 访问接口需要的角色（为空则不需要校验）
     */
    private String[] requiresRoles;

    /**
     * 访问接口需要的权限（为空则不需要校验）
     */
    private String[] requiresPermissions;

}