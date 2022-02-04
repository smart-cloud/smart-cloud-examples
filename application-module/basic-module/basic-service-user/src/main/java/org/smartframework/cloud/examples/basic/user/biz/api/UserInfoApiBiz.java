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
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.user.entity.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.springframework.stereotype.Repository;

@Repository
@DS(DataSourceName.BASIC_USER)
public class UserInfoApiBiz extends BaseBiz<UserInfoBaseMapper, UserInfoEntity> {

    /**
     * 插入用户信息
     *
     * @param userInfo
     * @return
     */
    public UserInfoEntity insert(UserInfoInsertReqVO userInfo) {
        UserInfoEntity userInfoEntity = super.buildEntity();
        userInfoEntity.setMobile(userInfo.getMobile());
        userInfoEntity.setNickName(userInfo.getNickname());
        userInfoEntity.setRealName(userInfo.getRealname());
        userInfoEntity.setSex(userInfo.getSex());
        userInfoEntity.setBirthday(userInfo.getBirthday());
        userInfoEntity.setProfileImage(userInfo.getProfileImage());
        userInfoEntity.setChannel(userInfo.getChannel());
        super.save(userInfoEntity);

        return userInfoEntity;
    }

    /**
     * 判断改手机号是否已存在
     *
     * @param mobile
     * @return
     */
    public boolean existByMobile(String mobile) {
        LambdaQueryWrapper<UserInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserInfoEntity::getId).eq(UserInfoEntity::getMobile, mobile);

        return super.getOne(wrapper) != null;
    }

}