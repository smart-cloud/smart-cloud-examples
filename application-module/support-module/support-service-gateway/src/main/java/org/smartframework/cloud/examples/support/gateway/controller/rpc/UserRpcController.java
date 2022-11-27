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

import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.support.gateway.service.rpc.UserRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author collin
 * @date 2020-09-11
 */
@Validated
@RestController
@RequiredArgsConstructor
public class UserRpcController implements UserRpc {

    private final UserRpcService userRpcService;

    /**
     * 登录（或注册）成功后缓存用户信息
     *
     * @param req
     * @return
     */
    @Override
    public Response<Void> cacheUserInfo(CacheUserInfoReqDTO req) {
        userRpcService.cacheUserInfo(req);
        return RespUtil.success();
    }

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @Override
    public Response<Void> exit(ExitLoginReqDTO req) {
        userRpcService.exit(req);
        return RespUtil.success();
    }

}