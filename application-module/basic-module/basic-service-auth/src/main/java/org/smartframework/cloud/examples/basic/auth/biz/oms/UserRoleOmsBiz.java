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
package org.smartframework.cloud.examples.basic.auth.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.entity.base.UserRoleRelaEntity;
import org.smartframework.cloud.examples.basic.auth.mapper.base.UserRoleRelaBaseMapper;
import org.smartframework.cloud.examples.basic.auth.mapper.oms.UserRoleOmsMapper;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.plus.enums.DeleteState;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@DS(DataSourceName.BASIC_AUTH)
@RequiredArgsConstructor
public class UserRoleOmsBiz extends BaseBiz<UserRoleRelaBaseMapper, UserRoleRelaEntity> {

    private final UserRoleOmsMapper userRoleOmsMapper;

    /**
     * 添加用户权限
     *
     * @param uid
     * @param roleIds
     * @param createUid
     * @return
     */
    public Boolean create(Long uid, Set<Long> roleIds, Long createUid) {
        List<UserRoleRelaEntity> userRoleRelaEntities = roleIds.stream().map(roleId -> {
            UserRoleRelaEntity entity = super.buildEntity();
            entity.setRoleInfoId(roleId);
            entity.setUserInfoId(uid);
            entity.setInsertUser(createUid);
            return entity;
        }).collect(Collectors.toList());

        return super.saveBatch(userRoleRelaEntities);
    }

    /**
     * 逻辑删除角色权限
     *
     * @param uid 用户id
     * @return
     */
    public Boolean logicDelete(Long uid) {
        UserRoleRelaEntity deletedEntity = new UserRoleRelaEntity();
        deletedEntity.setDelUser(UserContext.getUserId());
        deletedEntity.setDelTime(new Date());
        deletedEntity.setDelState(DeleteState.DELETED);
        return super.update(deletedEntity, new LambdaQueryWrapper<UserRoleRelaEntity>()
                .eq(UserRoleRelaEntity::getUserInfoId, uid)
                .eq(UserRoleRelaEntity::getDelState, DeleteState.NORMAL));
    }

    /**
     * 查询用户所拥有的角色权限
     *
     * @param uid
     * @return
     */
    public List<UserRoleRespVO> listRole(Long uid) {
        return userRoleOmsMapper.listRole(uid);
    }

    /**
     * 根据uid查询角色id
     *
     * @param uid
     * @return
     */
    public Set<Long> listRoleId(Long uid) {
        List<UserRoleRelaEntity> relaEntities = super.list(new LambdaQueryWrapper<UserRoleRelaEntity>()
                .select(UserRoleRelaEntity::getRoleInfoId)
                .eq(UserRoleRelaEntity::getUserInfoId, uid)
                .eq(UserRoleRelaEntity::getDelState, DeleteState.NORMAL));
        if (CollectionUtils.isEmpty(relaEntities)) {
            return new HashSet<>(0);
        }
        return relaEntities.stream().map(UserRoleRelaEntity::getRoleInfoId).collect(Collectors.toSet());
    }

}