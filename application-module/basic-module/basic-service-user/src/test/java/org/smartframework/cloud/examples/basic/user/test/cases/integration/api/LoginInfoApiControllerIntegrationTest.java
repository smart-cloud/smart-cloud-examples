package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.ExitReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.test.data.LoginInfoData;
import org.smartframework.cloud.examples.basic.user.test.data.UserInfoData;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
public class LoginInfoApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private LoginInfoData loginInfoData;
    @Autowired
    private UserInfoData userInfoData;
    @MockBean
    private UserRpc userRpc;

    @Test
    public void testLogin() throws Exception {
        // mock start
        Mockito.when(userRpc.cacheUserInfo(ArgumentMatchers.any())).thenReturn(RespUtil.success());
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

        RespVO<LoginRespVO> result = super.post("/user/api/loginInfo/login", reqVO,
                new TypeReference<RespVO<LoginRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(result.getBody().getUserId()).isNotNull();
        Assertions.assertThat(result.getBody().getUsername()).isNotBlank();
        Assertions.assertThat(result.getBody().getRealName()).isNotBlank();
        Assertions.assertThat(result.getBody().getMobile()).isNotBlank();
    }

    @Test
    public void testExit() throws Exception {
        // mock start
        Mockito.when(userRpc.exit(ArgumentMatchers.any())).thenReturn(RespUtil.success());
        // mock end

        // 构造请求参数
        ExitReqVO reqVO = new ExitReqVO();
        reqVO.setToken("test");

        RespVO<LoginRespVO> result = super.post("/user/api/loginInfo/exit", reqVO,
                new TypeReference<RespVO<LoginRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
    }

}