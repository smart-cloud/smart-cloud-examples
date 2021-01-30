package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.gateway.bo.SecurityKeyBO;
import org.smartframework.cloud.examples.support.gateway.constants.RedisExpire;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author liyulin
 * @date 2020-09-14
 */
public class UserRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testCacheUserInfo() throws Exception {
        String token = "12341234";
        Long userId = 1L;
        // mock start
        RMapCache<String, SecurityKeyBO> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        authCache.put(RedisKeyHelper.getSecurityKey(token), new SecurityKeyBO(), RedisExpire.SECURITY_KEY_EXPIRE_MILLIS_NON_LOGIN, TimeUnit.SECONDS);

        RMapCache<Long, String> userTokenCache = redissonClient.getMapCache(RedisKeyHelper.getUserTokenRelationHashKey());
        userTokenCache.put(RedisKeyHelper.getUserTokenRelationKey(userId), "12313", RedisExpire.USER_EXPIRE_MILLIS_LOGIN_SUCCESS, TimeUnit.SECONDS);
        // mock end

        CacheUserInfoReqVO req = CacheUserInfoReqVO.builder()
                .token(token)
                .userId(userId)
                .username("zhangsan")
                .mobile("13112345678")
                .realName("张三")
                .build();

        RespVO<Base> result = post("/gateway/rpc/user/cacheUserInfo", req, new TypeReference<RespVO<Base>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
    }

}