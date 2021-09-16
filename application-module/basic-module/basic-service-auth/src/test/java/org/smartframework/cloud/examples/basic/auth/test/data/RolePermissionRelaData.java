package org.smartframework.cloud.examples.basic.auth.test.data;

import org.smartframework.cloud.examples.basic.auth.biz.oms.RolePermissionOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.base.RolePermissionRelaEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.constants.DelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolePermissionRelaData {

    @Autowired
    private RolePermissionOmsBiz rolePermissionOmsBiz;

    public void insert(Long roleId, List<Long> permissionIds) {
        List<RolePermissionRelaEntity> rolePermissionRelaEntities = permissionIds.stream().map(permissionId -> {
            RolePermissionRelaEntity userRoleRelaEntity = new RolePermissionRelaEntity();
            userRoleRelaEntity.setRoleInfoId(roleId);
            userRoleRelaEntity.setPermissionInfoId(permissionId);
            userRoleRelaEntity.setInsertTime(new Date());
            userRoleRelaEntity.setInsertUser(1L);
            userRoleRelaEntity.setDelState(DelState.NORMAL);

            return userRoleRelaEntity;
        }).collect(Collectors.toList());

        rolePermissionOmsBiz.insertBatchSomeColumn(rolePermissionRelaEntities);
    }

}