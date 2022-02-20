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
package org.smartframework.cloud.examples.basic.rpc.auth;

import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.basic.rpc.constant.RpcConstants;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;

/**
 * 权限rpc相关接口
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Auth.FEIGN_CLIENT_NAME, contextId = "authRpc")
public interface AuthRpc {

    /**
     * 根据uid查询用户拥有的权限信息
     *
     * @param uid
     * @return
     */
    @GetMapping("auth/rpc/auth/listByUid")
    Response<AuthRespDTO> listByUid(@NotNull Long uid);

}