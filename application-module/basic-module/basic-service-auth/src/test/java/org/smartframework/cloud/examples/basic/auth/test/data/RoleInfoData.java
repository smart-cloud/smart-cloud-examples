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

import io.github.smart.cloud.starter.global.id.GlobalId;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RoleInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.entity.RoleInfoEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class RoleInfoData {

    private final RoleInfoOmsBiz roleInfoOmsBiz;

    public RoleInfoEntity insert() {
        RoleInfoEntity entity = new RoleInfoEntity();
        entity.setId(GlobalId.nextId());
        entity.setInsertTime(new Date());
        entity.setDelState(DeleteState.NORMAL);
        entity.setCode("admin");
        entity.setDescription("管理员");
        entity.setInsertUser(1L);

        roleInfoOmsBiz.save(entity);
        return entity;
    }

}