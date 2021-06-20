package org.smartframework.cloud.examples.basic.user.test.cases.integration.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
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
        Long userId = 1L;
        userInfoData.insertTestData(userId);

        Response<UserInfoBaseRespVO> result = super.get("/user/api/userInfo/query", null,
                new TypeReference<Response<UserInfoBaseRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
    }

}