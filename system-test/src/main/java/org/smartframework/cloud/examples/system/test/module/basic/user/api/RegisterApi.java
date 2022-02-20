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
package org.smartframework.cloud.examples.system.test.module.basic.user.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.utility.HttpUtil;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;

import java.io.IOException;

/**
 * 注册
 *
 * @author collin
 * @date 2020-10-10
 */
@UtilityClass
public class RegisterApi {

    public Response<RegisterUserRespVO> register(RegisterUserReqVO reqVO) throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "user/api/register/register",
                reqVO, new TypeReference<Response<RegisterUserRespVO>>() {
                });
    }

}