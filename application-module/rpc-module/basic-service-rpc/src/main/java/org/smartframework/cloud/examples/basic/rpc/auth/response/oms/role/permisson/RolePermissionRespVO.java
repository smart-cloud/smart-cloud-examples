package org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Set;

/**
 * 角色权限信息
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RolePermissionRespVO extends Base {

    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 权限
     */
    private Set<PermissionInfoRespVO> permissions;

}