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
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.test.data.UserInfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class UserInfoApiControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private UserInfoData userInfoData;

    @Test
    void testQuery() throws Exception {
        Long userId = 1L;
        userInfoData.insertTestData(userId);

        Response<UserInfoBaseRespVO> result = super.get("/user/api/userInfo/query", null,
                new TypeReference<Response<UserInfoBaseRespVO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
    }

}