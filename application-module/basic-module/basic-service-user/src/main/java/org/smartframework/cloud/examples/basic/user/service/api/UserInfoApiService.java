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
package org.smartframework.cloud.examples.basic.user.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.smart.cloud.exception.ParamValidateException;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.biz.api.UserInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.constants.UserReturnCodes;
import org.smartframework.cloud.examples.basic.user.entity.UserInfoEntity;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@DS(DataSourceName.BASIC_USER_MASTER)
public class UserInfoApiService {

    private final UserInfoApiBiz userInfoApiBiz;

    /**
     * 根据id查询用户信息
     *
     * @return
     */
    @DS(DataSourceName.BASIC_USER_SLAVE)
    public UserInfoBaseRespVO queryById() {
        Long userId = UserContext.getUserId();
        UserInfoEntity userInfoEntity = userInfoApiBiz.getById(userId);
        UserInfoBaseRespVO userInfoBaseRespVO = new UserInfoBaseRespVO();
        BeanUtils.copyProperties(userInfoEntity, userInfoBaseRespVO);
        return userInfoBaseRespVO;
    }

    /**
     * 插入用户信息
     *
     * @param userInfo
     * @return
     */
    public UserInfoEntity insert(UserInfoInsertReqVO userInfo) {
        boolean existMobile = userInfoApiBiz.existByMobile(userInfo.getMobile());
        if (existMobile) {
            throw new ParamValidateException(UserReturnCodes.REGISTER_MOBILE_EXSITED);
        }

        return userInfoApiBiz.insert(userInfo);
    }

}