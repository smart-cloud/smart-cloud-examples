package org.smartframework.cloud.examples.system.test.module.basic.user.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

/**
 * 注册
 *
 * @author liyulin
 * @date 2020-10-10
 */
@UtilityClass
public class RegisterApi {

    public RespVO<RegisterUserRespVO> register(RegisterUserReqVO reqVO) throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "user/api/register/register",
                reqVO, new TypeReference<RespVO<RegisterUserRespVO>>() {
                });
    }

}