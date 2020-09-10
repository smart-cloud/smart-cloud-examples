package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.PwdStateEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.starter.test.core.WebMvcIntegrationTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
public class RegisterApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @MockBean
    private LoginInfoApiService loginInfoApiService;

    @Test
    public void testRegister() throws Exception {
        // mock
        Mockito.doNothing().when(loginInfoApiService).cacheLoginAfterLoginSuccess(Mockito.any());

        // 构造请求参数
        UserInfoInsertReqVO userInfo = new UserInfoInsertReqVO();
        userInfo.setMobile("18720912981");
        userInfo.setChannel(ChannelEnum.APP.getValue());
        userInfo.setSex(SexEnum.FEMALE.getValue());

        LoginInfoInsertReqVO loginInfo = new LoginInfoInsertReqVO();
        loginInfo.setUsername("zhangsan");
        loginInfo.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
        loginInfo.setPassword("123456");

        RegisterUserReqVO registerUserReqVO = new RegisterUserReqVO();
        registerUserReqVO.setUserInfo(userInfo);
        registerUserReqVO.setLoginInfo(loginInfo);

        RespVO<RegisterUserRespVO> result = super.post("/user/api/register", registerUserReqVO,
                new TypeReference<RespVO<RegisterUserRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
    }

}