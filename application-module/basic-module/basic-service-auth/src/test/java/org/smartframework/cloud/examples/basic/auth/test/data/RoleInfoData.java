package org.smartframework.cloud.examples.basic.auth.test.data;

import org.smartframework.cloud.examples.basic.auth.biz.oms.RoleInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleInfoData {

    @Autowired
    private RoleInfoOmsBiz roleInfoOmsBiz;

    public RoleInfoEntity insert() {
        RoleInfoEntity entity = roleInfoOmsBiz.create();
        entity.setCode("admin");
        entity.setDescription("管理员");
        entity.setInsertUser(1L);

        roleInfoOmsBiz.save(entity);
        return entity;
    }

}