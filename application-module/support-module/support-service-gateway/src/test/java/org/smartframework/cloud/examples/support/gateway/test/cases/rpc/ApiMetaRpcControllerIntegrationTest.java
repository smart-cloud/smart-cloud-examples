package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.response.api.GenerateClientPubKeyRespVO;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;
import org.smartframework.cloud.utility.JacksonUtil;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyulin
 * @date 2020-09-12
 */
@Slf4j
public class ApiMetaRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @Test
    public void testUpload() throws Exception {
        Map<String, ApiMetaUploadReqVO.ApiAC> apiACs = new HashMap<>();
        apiACs.put("/user/api/login/checkPOST", ApiMetaUploadReqVO.ApiAC.builder().auth(false).tokenCheck(true).decrypt(false).build());
        apiACs.put("/user/api/register/registerPOST", ApiMetaUploadReqVO.ApiAC.builder().auth(false).tokenCheck(true).decrypt(false).build());

        ApiMetaUploadReqVO req = new ApiMetaUploadReqVO(apiACs);

        RespVO<Base> result = post("/gateway/rpc/apiMeta/upload", req, new TypeReference<RespVO<Base>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
    }

    public <T> T post1(String url, Object req, TypeReference<T> typeReference) throws Exception {
        String requestJsonStr = JacksonUtil.toJson(req);
        log.info("test.requestBody={}", requestJsonStr);
        WebTestClient.RequestBodySpec requestBodySpec = webTestClient.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON);
        if (req != null) {
            requestBodySpec.bodyValue(req);
        }
        byte[] resultBytes = requestBodySpec.acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .returnResult()
                .getResponseBody();

        String result = new String(resultBytes);
        log.info("test.result={}", result);

        return JacksonUtil.parseObject(result, typeReference);
    }
}