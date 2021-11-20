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
package org.smartframework.cloud.examples.support.rpc.gateway;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.support.rpc.constant.RpcConstants;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 用户rpc相关接口
 *
 * @author collin
 * @date 2020-09-10
 * @status done
 */
@SmartFeignClient(name = RpcConstants.Gateway.FEIGN_CLIENT_NAME, contextId = "userRpc")
public interface UserRpc {

    /**
     * 登录（或注册）成功后缓存用户信息
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/user/cacheUserInfo")
    Response<Base> cacheUserInfo(@RequestBody @Valid CacheUserInfoReqDTO req);

    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @PostMapping("gateway/rpc/user/exit")
    Response<Base> exit(@RequestBody @Valid ExitLoginReqDTO req);

}
