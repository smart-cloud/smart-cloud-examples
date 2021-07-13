package org.smartframework.cloud.examples.basic.auth.test.data;

import org.smartframework.cloud.examples.basic.auth.biz.oms.UserRoleOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.base.UserRoleRelaEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleRelaData {

    @Autowired
    private UserRoleOmsBiz userRoleOmsBiz;

    public void insert(Long uid, List<Long> roleIds) {
        List<UserRoleRelaEntity> userRoleRelaEntities = roleIds.stream().map(roleId -> {
            UserRoleRelaEntity userRoleRelaEntity = new UserRoleRelaEntity();
            userRoleRelaEntity.setUserInfoId(uid);
            userRoleRelaEntity.setRoleInfoId(roleId);
            userRoleRelaEntity.setInsertTime(new Date());
            userRoleRelaEntity.setInsertUser(1L);
            userRoleRelaEntity.setDelState(DelState.NORMAL);


            return userRoleRelaEntity;
        }).collect(Collectors.toList());

        userRoleOmsBiz.insertBatchSomeColumn(userRoleRelaEntities);
    }

}