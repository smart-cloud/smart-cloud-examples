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
package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.api.core.annotation.enums.SignType;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.test.core.integration.WebReactiveIntegrationTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.examples.api.ac.core.vo.*;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqDTO;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author collin
 * @date 2020-09-12
 */
class ApiMetaRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @MockBean
    private DiscoveryClient discoveryClient;
    @SpyBean
    private ApiMetaRpcService apiMetaRpcService;

    @Test
    void testNotifyFetch() throws Exception {
        String serviceId = "user";
        // mock start
        ServiceInstance instanceInfoMock = new DefaultServiceInstance(serviceId, serviceId, "192.168.15.10", 8080, false, null);
        Mockito.when(discoveryClient.getInstances(serviceId)).thenReturn(Lists.newArrayList(instanceInfoMock));

        Map<String, ApiAccessMetaRespVO> apiAccessMapMock = new HashMap<>();

        ApiAccessMetaRespVO loginApiAccessMeta = ApiAccessMetaRespVO.builder()
                .authMeta(AuthMetaRespVO.builder().requireUser(true).requirePermissions(new String[0]).requireRoles(new String[0]).build())
                .dataSecurityMeta(DataSecurityMetaRespVO.builder().requestDecrypt(true).responseEncrypt(true).sign(SignType.ALL.getType()).build())
                .repeatSubmitCheckMeta(RepeatSubmitCheckMetaRespVO.builder().check(true).expireMillis(10000L).build()).build();
        apiAccessMapMock.put("/user/api/login/checkPOST", loginApiAccessMeta);

        ApiAccessMetaRespVO registerApiAccessMeta = ApiAccessMetaRespVO.builder()
                .authMeta(AuthMetaRespVO.builder().requireUser(false).requirePermissions(new String[0]).requireRoles(new String[0]).build())
                .dataSecurityMeta(DataSecurityMetaRespVO.builder().requestDecrypt(true).responseEncrypt(true).sign(SignType.ALL.getType()).build())
                .repeatSubmitCheckMeta(RepeatSubmitCheckMetaRespVO.builder().check(true).expireMillis(10000L).build()).build();
        apiAccessMapMock.put("/user/api/register/registerPOST", registerApiAccessMeta);
        Response<ApiMetaFetchRespVO> apiMetaFetchResponseMock = new Response(new ApiMetaFetchRespVO(apiAccessMapMock));
        Mockito.doReturn(apiMetaFetchResponseMock).when(apiMetaRpcService).fetchApiMeta(Mockito.anyString());
        // mock end

        Response<Void> result = post("/gateway/rpc/apiMeta/notifyFetch", new NotifyFetchReqDTO(serviceId), new TypeReference<Response<Void>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

}