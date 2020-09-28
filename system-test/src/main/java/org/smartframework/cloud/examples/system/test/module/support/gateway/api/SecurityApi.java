package org.smartframework.cloud.examples.system.test.module.support.gateway.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.vo.RespVO;
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

    public RespVO<GenerateClientPubKeyRespVO> generateClientPubKey() throws IOException {
        return HttpUtil.get(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateClientPubKey",
                null, new TypeReference<RespVO<GenerateClientPubKeyRespVO>>() {
                });
    }

    public RespVO<GenerateAesKeyRespVO> generateAesKey(GenerateAesKeyReqVO reqVO) throws IOException {
        return HttpUtil.get(
                SystemTestConfig.getGatewayBaseUrl() + "gateway/api/security/generateAesKey",
                reqVO, new TypeReference<RespVO<GenerateAesKeyRespVO>>() {
                });
    }

}