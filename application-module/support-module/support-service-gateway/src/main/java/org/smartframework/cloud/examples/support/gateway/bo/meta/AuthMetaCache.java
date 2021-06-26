package org.smartframework.cloud.examples.support.gateway.bo.meta;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Set;

/**
 * 接口鉴权meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
public class AuthMetaCache extends Base {

    /**
     * 是否需要登陆校验（false则不需要校验）
     */
    private boolean requiresUser;

    /**
     * 访问接口需要的角色（为空则不需要校验）
     */
    private Set<String> requiresRoles;

    /**
     * 访问接口需要的权限（为空则不需要校验）
     */
    private Set<String> requiresPermissions;

}