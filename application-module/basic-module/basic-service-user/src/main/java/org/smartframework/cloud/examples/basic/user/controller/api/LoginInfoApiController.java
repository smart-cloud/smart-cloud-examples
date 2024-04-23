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
package org.smartframework.cloud.examples.basic.user.controller.api;

import io.github.smart.cloud.api.core.annotation.RequireDataSecurity;
import io.github.smart.cloud.api.core.annotation.RequireTimestamp;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.ExitReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 登陆
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@Validated
@RestController
@RequestMapping("user/api/loginInfo")
@RequiredArgsConstructor
public class LoginInfoApiController {

    private final LoginInfoApiService loginInfoApiService;

    /**
     * 登陆
     *
     * @param req
     * @return
     */
    @PostMapping("login")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<LoginRespVO> login(@RequestBody @Valid LoginReqVO req) {
        return ResponseUtil.success(loginInfoApiService.login(req));
    }


    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @PostMapping("exit")
    @RequireTimestamp
    public Response<Void> exit(@RequestBody @Valid ExitReqVO req) {
        loginInfoApiService.exit(req);
        return ResponseUtil.success();
    }

}