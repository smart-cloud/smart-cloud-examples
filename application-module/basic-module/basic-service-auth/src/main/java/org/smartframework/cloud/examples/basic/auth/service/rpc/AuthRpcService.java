package org.smartframework.cloud.examples.basic.auth.service.rpc;

import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.examples.basic.auth.biz.oms.PermissionInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RoleInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RolePermissionOmsBiz;
import org.smartframework.cloud.examples.basic.auth.biz.oms.UserRoleOmsBiz;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthRpcService {

    @Autowired
    private UserRoleOmsBiz userRoleOmsBiz;
    @Autowired
    private RolePermissionOmsBiz rolePermissionOmsBiz;
    @Autowired
    private RoleInfoOmsBiz roleInfoOmsBiz;
    @Autowired
    private PermissionInfoOmsBiz permissionInfoOmsBiz;

    /**
     * 根据uid查询用户拥有的权限信息
     *
     * @param uid
     * @return
     */
    public AuthRespDTO listByUid(Long uid) {
        Set<Long> roleIds = userRoleOmsBiz.listRoleId(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }

        Set<String> roleCodes = roleInfoOmsBiz.listCode(roleIds);
        if (CollectionUtils.isEmpty(roleCodes)) {
            return null;
        }

        Set<Long> permissionIds = rolePermissionOmsBiz.listPermissionIds(roleIds);
        Set<String> permissionCodes = null;
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            permissionCodes = permissionInfoOmsBiz.listCode(permissionIds);
        }

        AuthRespDTO authRespDTO = new AuthRespDTO();
        authRespDTO.setRoles(roleCodes);
        authRespDTO.setPermissions(permissionCodes);

        return authRespDTO;
    }

}