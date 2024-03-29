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
package org.smartframework.cloud.examples.system.test.module.support.gateway.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.utility.HttpUtil;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;

import java.io.IOException;

/**
 * @author collin
 * @date 2020-09-29
 */
@UtilityClass
public class SecurityApi {

    public Response<GenerateClientPubKeyRespVO> generateClientPubKey() throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateClientPubKey",
                null, new TypeReference<Response<GenerateClientPubKeyRespVO>>() {
                });
    }

    public Response<GenerateAesKeyRespVO> generateAesKey(GenerateAesKeyReqVO reqVO) throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateAesKey",
                reqVO, new TypeReference<Response<GenerateAesKeyRespVO>>() {
                });
    }

}