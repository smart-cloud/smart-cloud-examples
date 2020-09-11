package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.app.auth.core.UserBO;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.test.data.UserInfoData;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
public class UserInfoApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private UserInfoData userInfoData;

    @Test
    public void testQuery() throws Exception {
        UserContext.setContext(UserBO.builder().id(1L).mobile("13112345678").realName("张三").build());

        Long userId = 1L;
        userInfoData.insertTestData(userId);

        RespVO<UserInfoBaseRespVO> result = super.get("/user/api/userInfo/query", null,
                new TypeReference<RespVO<UserInfoBaseRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
    }

}