package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyulin
 * @date 2020-09-12
 */
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

}