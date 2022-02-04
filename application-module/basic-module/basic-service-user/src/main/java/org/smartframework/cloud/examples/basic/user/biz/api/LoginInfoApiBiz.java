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
package org.smartframework.cloud.examples.basic.user.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.entity.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.LoginInfoBaseMapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@DS(DataSourceName.BASIC_USER)
public class LoginInfoApiBiz extends BaseBiz<LoginInfoBaseMapper, LoginInfoEntity> {

    /**
     * 插入登陆信息
     *
     * @param bo
     * @return
     */
    public LoginInfoEntity insert(LoginInfoInsertBizBO bo) {
        LoginInfoEntity entity = super.buildEntity();
        entity.setUserId(bo.getUserId());
        entity.setUsername(bo.getUsername());
        entity.setSalt(bo.getSalt());
        entity.setPassword(bo.getPassword());
        entity.setPwdState(bo.getPwdState());
        entity.setLastLoginTime(new Date());
        entity.setUserState(UserStateEnum.ENABLE.getValue());
        super.save(entity);
        return entity;
    }

    /**
     * 根据用户名查询登陆信息
     *
     * @param username
     * @return
     */
    public LoginInfoEntity queryByUsername(String username) {
        LambdaQueryWrapper<LoginInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoginInfoEntity::getUsername, username);

        return super.getOne(wrapper);
    }

    /**
     * 判断该用户名是否已存在
     *
     * @param username
     * @return
     */
    public boolean existByUsername(String username) {
        LambdaQueryWrapper<LoginInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(LoginInfoEntity::getId).eq(LoginInfoEntity::getUsername, username);

        return super.getOne(wrapper) != null;
    }

}