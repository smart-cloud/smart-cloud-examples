package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
public class RegisterApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private LoginInfoApiService loginInfoApiService;
    @MockBean
    private UserRpc userRpc;

    @Test
    public void testRegister() throws Exception {
        // mock start
        Mockito.when(userRpc.cacheUserInfo(ArgumentMatchers.any())).thenReturn(RespUtil.success());
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

        RespVO<RegisterUserRespVO> result = super.post("/user/api/register/register", registerUserReqVO,
                new TypeReference<RespVO<RegisterUserRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getUserId()).isNotNull();
        Assertions.assertThat(result.getBody().getUsername()).isNotBlank();
        Assertions.assertThat(result.getBody().getRealName()).isNotBlank();
        Assertions.assertThat(result.getBody().getMobile()).isNotBlank();
    }

}