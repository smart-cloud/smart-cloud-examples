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
import com.google.common.collect.Sets;
import io.github.smart.cloud.common.pojo.Base;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.starter.redis.adapter.IRedisAdapter;
import io.github.smart.cloud.test.core.integration.WebReactiveIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.RedisTtl;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.springframework.beans.factory.annotation.Autowired;

class UserRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @Autowired
    private IRedisAdapter redisAdapter;

    @Test
    void testCacheUserInfo() throws Exception {
        String token = "12341234";
        Long userId = 1L;
        // mock start
        String cacheKey = RedisKeyHelper.getSecurityKey(token);
        redisAdapter.set(cacheKey, new SecurityKeyCache(), RedisTtl.SECURITY_KEY_NON_LOGIN);

        String userTokenRelationKey = RedisKeyHelper.getUserTokenRelationKey(userId);
        redisAdapter.set(userTokenRelationKey, "12313", RedisTtl.USER_LOGIN_SUCCESS);
        // mock end

        CacheUserInfoReqDTO req = CacheUserInfoReqDTO.builder()
                .token(token)
                .uid(userId)
                .username("zhangsan")
                .mobile("13112345678")
                .realName("张三")
                .roles(Sets.newHashSet("admin", "pm"))
                .permissions(Sets.newHashSet("/user/api/register/register"))
                .build();

        Response<Base> result = post("/gateway/rpc/user/cacheUserInfo", req, new TypeReference<Response<Base>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
    }

}