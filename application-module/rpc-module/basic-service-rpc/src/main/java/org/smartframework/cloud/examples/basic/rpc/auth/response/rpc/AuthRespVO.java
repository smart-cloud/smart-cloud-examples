package org.smartframework.cloud.examples.basic.rpc.auth.response.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthRespVO extends Base {

    /**
     * 用户所拥有的角色
     */
    private Set<String> roles;
    /**
     * 用户所拥有的权限
     */
    private Set<String> permissions;

}