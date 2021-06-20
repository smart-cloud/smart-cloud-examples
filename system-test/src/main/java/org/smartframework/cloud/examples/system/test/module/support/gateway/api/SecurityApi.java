package org.smartframework.cloud.examples.system.test.module.support.gateway.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.support.rpc.gateway.request.api.GenerateAesKeyReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateAesKeyRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

/**
 * @author liyulin
 * @date 2020-09-29
 */
@UtilityClass
public class SecurityApi {

    public Response<GenerateClientPubKeyRespVO> generateClientPubKey() throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateClientPubKey",
                null, new TypeReference<Response<GenerateClientPubKeyRespVO>>() {
                });
    }

    public Response<GenerateAesKeyRespVO> generateAesKey(GenerateAesKeyReqVO reqVO) throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateAesKey",
                reqVO, new TypeReference<Response<GenerateAesKeyRespVO>>() {
                });
    }

}