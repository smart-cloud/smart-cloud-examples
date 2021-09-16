package org.smartframework.cloud.examples.basic.auth.test.data;

import org.smartframework.cloud.examples.basic.auth.biz.oms.PermissionInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionInfoData {

    @Autowired
    private PermissionInfoOmsBiz permissionInfoOmsBiz;

    public PermissionInfoEntity insert() {
        PermissionInfoEntity entity = permissionInfoOmsBiz.buildEntity();
        entity.setCode("/auth/oms/permission/create");
        entity.setDescription("创建权限");
        entity.setInsertUser(1L);

        permissionInfoOmsBiz.save(entity);
        return entity;
    }

}