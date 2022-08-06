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
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.token.RenewReqVO;
import org.smartframework.cloud.examples.basic.user.service.api.TokenApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("user/api/token")
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenApiService tokenApiService;

    /**
     * token续期
     *
     * @param req
     * @return
     */
    @PostMapping("renew")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> renew(@RequestBody @Valid RenewReqVO req) {
        return RespUtil.success(tokenApiService.renew(req.getToken()));
    }

}