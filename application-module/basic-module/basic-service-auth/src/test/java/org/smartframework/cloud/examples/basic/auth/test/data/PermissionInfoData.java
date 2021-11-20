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