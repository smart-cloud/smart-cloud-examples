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
package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.ExitReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.entity.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.test.data.LoginInfoData;
import org.smartframework.cloud.examples.basic.user.test.data.UserInfoData;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class LoginInfoApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private LoginInfoData loginInfoData;
    @Autowired
    private UserInfoData userInfoData;
    @MockBean
    private UserRpc userRpc;
    @MockBean
    private AuthRpc authRpc;

    @Test
    void testLogin() throws Exception {
        // mock start
        Mockito.when(userRpc.cacheUserInfo(ArgumentMatchers.any())).thenReturn(RespUtil.success());
        Mockito.when(authRpc.listByUid(ArgumentMatchers.any()))
                .thenReturn(RespUtil.success(AuthRespDTO.builder()
                        .roles(Sets.newSet("admin"))
                        .permissions(Sets.newSet("/user/api/register/register"))
                        .build()));
        // mock end

        // insert login info
        String username = "zhangsan";
        String password = "123456";
        LoginInfoEntity loginInfoEntity = loginInfoData.insert(username, password);
        userInfoData.insertTestData(loginInfoEntity.getUserId());

        // 构造请求参数
        LoginReqVO reqVO = new LoginReqVO();
        reqVO.setToken("test");
        reqVO.setUsername(username);
        reqVO.setPassword(password);

        Response<LoginRespVO> result = super.post("/user/api/loginInfo/login", reqVO,
                new TypeReference<Response<LoginRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody().getUserId()).isNotNull();
        Assertions.assertThat(result.getBody().getUsername()).isNotBlank();
        Assertions.assertThat(result.getBody().getRealName()).isNotBlank();
        Assertions.assertThat(result.getBody().getMobile()).isNotBlank();
    }

    @Test
    void testExit() throws Exception {
        // mock start
        Mockito.when(userRpc.exit(ArgumentMatchers.any())).thenReturn(RespUtil.success());
        // mock end

        // 构造请求参数
        ExitReqVO reqVO = new ExitReqVO();
        reqVO.setToken("test");

        Response<LoginRespVO> result = super.post("/user/api/loginInfo/exit", reqVO,
                new TypeReference<Response<LoginRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

}