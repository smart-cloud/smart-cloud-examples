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
package org.smartframework.cloud.examples.support.gateway.controller.rpc;

import io.github.smart.cloud.common.pojo.Base;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class ApiMetaRpcController implements ApiMetaRpc {

    private final ApiMetaRpcService apiMetaRpcService;

    @Override
    public Response<Base> notifyFetch(NotifyFetchReqDTO req) {
        try {
            apiMetaRpcService.notifyFetch(req);
        } catch (IOException | InterruptedException e) {
            log.error("notifyFetch.fail", e);
            return RespUtil.error(e.getMessage());
        }
        return RespUtil.success();
    }

}