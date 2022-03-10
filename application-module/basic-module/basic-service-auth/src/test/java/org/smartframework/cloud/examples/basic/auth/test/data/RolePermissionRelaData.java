/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.basic.auth.test.data;

import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RolePermissionOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.RolePermissionRelaEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RolePermissionRelaData {

    private final RolePermissionOmsBiz rolePermissionOmsBiz;

    public void insert(Long roleId, List<Long> permissionIds) {
        List<RolePermissionRelaEntity> rolePermissionRelaEntities = permissionIds.stream().map(permissionId -> {
            RolePermissionRelaEntity userRoleRelaEntity = new RolePermissionRelaEntity();
            userRoleRelaEntity.setRoleInfoId(roleId);
            userRoleRelaEntity.setPermissionInfoId(permissionId);
            userRoleRelaEntity.setInsertTime(new Date());
            userRoleRelaEntity.setInsertUser(1L);
            userRoleRelaEntity.setDelState(DeleteState.NORMAL);

            return userRoleRelaEntity;
        }).collect(Collectors.toList());

        rolePermissionOmsBiz.insertBatchSomeColumn(rolePermissionRelaEntities);
    }

}