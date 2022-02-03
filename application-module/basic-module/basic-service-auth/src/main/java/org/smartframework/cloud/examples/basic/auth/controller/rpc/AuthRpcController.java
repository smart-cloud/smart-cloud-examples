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
package org.smartframework.cloud.examples.basic.auth.controller.rpc;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.rpc.AuthRpcService;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class AuthRpcController implements AuthRpc {

    private final AuthRpcService authRpcService;

    @Override
    public Response<AuthRespDTO> listByUid(Long uid) {
        return RespUtil.success(authRpcService.listByUid(uid));
    }

}