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
import com.google.common.collect.Sets;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class RegisterApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private LoginInfoApiService loginInfoApiService;
    @MockBean
    private UserRpc userRpc;
    @MockBean
    private AuthRpc authRpc;

    @Test
    void testRegister() throws Exception {
        // mock start
        Mockito.when(userRpc.cacheUserInfo(ArgumentMatchers.any())).thenReturn(RespUtil.success());
        Mockito.when(authRpc.listByUid(ArgumentMatchers.any()))
                .thenReturn(RespUtil.success(AuthRespDTO.builder()
                        .roles(Sets.newHashSet("admin"))
                        .permissions(Sets.newHashSet("/user/api/register/register"))
                        .build()));
        // mock end

        // 构造请求参数
        UserInfoInsertReqVO userInfo = new UserInfoInsertReqVO();
        userInfo.setMobile("18720912981");
        userInfo.setChannel(ChannelEnum.APP.getValue());
        userInfo.setSex(SexEnum.FEMALE.getValue());
        userInfo.setRealname("李四");

        LoginInfoInsertReqVO loginInfo = new LoginInfoInsertReqVO();
        loginInfo.setUsername("zhangsan");
        loginInfo.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
        loginInfo.setPassword("123456");

        RegisterUserReqVO registerUserReqVO = new RegisterUserReqVO();
        registerUserReqVO.setToken("test");
        registerUserReqVO.setUserInfo(userInfo);
        registerUserReqVO.setLoginInfo(loginInfo);

        Response<RegisterUserRespVO> result = super.post("/user/api/register/register", registerUserReqVO,
                new TypeReference<Response<RegisterUserRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getUserId()).isNotNull();
        Assertions.assertThat(result.getBody().getUsername()).isNotBlank();
        Assertions.assertThat(result.getBody().getRealName()).isNotBlank();
        Assertions.assertThat(result.getBody().getMobile()).isNotBlank();
    }

}