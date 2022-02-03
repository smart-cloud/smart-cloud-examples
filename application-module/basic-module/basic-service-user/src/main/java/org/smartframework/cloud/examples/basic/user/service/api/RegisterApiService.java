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

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.springframework.stereotype.Service;

/**
 * 注册
 *
 * @author collin
 * @date 2019-06-29
 */
@Service
@RequiredArgsConstructor
public class RegisterApiService {

    private final UserInfoApiService userInfoApiService;
    private final LoginInfoApiService loginInfoApiService;

    /**
     * 注册
     *
     * @param req
     * @return
     */
    @DSTransactional
    public RegisterUserRespVO register(RegisterUserReqVO req) {
        // 用户信息
        UserInfoEntity userInfoEntity = userInfoApiService.insert(req.getUserInfo());

        // 登陆信息
        LoginInfoInsertReqVO loginInfo = req.getLoginInfo();

        LoginInfoInsertServiceBO loginInfoInsertBO = LoginInfoInsertServiceBO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfo.getUsername())
                .password(loginInfo.getPassword())
                .pwdState(loginInfo.getPwdState())
                .build();
        loginInfoApiService.insert(loginInfoInsertBO);

        RegisterUserRespVO registerUserRespVO = RegisterUserRespVO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfo.getUsername())
                .realName(userInfoEntity.getRealName())
                .mobile(userInfoEntity.getMobile())
                .build();

        // 缓存登录信息到网关
        loginInfoApiService.cacheUserInfo(req.getToken(), registerUserRespVO);

        return registerUserRespVO;
    }

}