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

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.RegisterApiService;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 注册
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@Validated
@RestController
@RequestMapping("user/api/register")
@RequiredArgsConstructor
public class RegisterApiController {

    private final RegisterApiService registerApiService;

    /**
     * 注册
     *
     * @param req
     * @return
     */
    @PostMapping("register")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<RegisterUserRespVO> register(@RequestBody @Valid RegisterUserReqVO req) {
        return RespUtil.success(registerApiService.register(req));
    }

}