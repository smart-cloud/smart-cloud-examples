package org.smartframework.cloud.examples.basic.rpc.auth.response.base;

import org.smartframework.cloud.common.pojo.BaseEntityResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 角色权限关系表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RolePermissionRelaBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** t_role_info表id */
	private Long roleInfoId;
	
    /** t_permission_info表id */
	private Long permissionInfoId;
	
}