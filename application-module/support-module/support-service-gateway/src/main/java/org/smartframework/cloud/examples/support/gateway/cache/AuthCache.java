package org.smartframework.cloud.examples.support.gateway.cache;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Set;

/**
 * 权限cache
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class AuthCache extends Base {

    /**
     * 用户所拥有的角色
     */
    private Set<String> roles;
    /**
     * 用户所拥有的权限
     */
    private Set<String> permissions;

}