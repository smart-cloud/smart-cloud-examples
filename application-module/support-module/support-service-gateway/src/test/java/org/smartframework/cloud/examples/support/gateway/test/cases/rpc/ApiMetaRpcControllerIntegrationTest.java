package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.*;
import org.smartframework.cloud.examples.support.gateway.service.rpc.ApiMetaRpcService;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqVO;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyulin
 * @date 2020-09-12
 */
public class ApiMetaRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @MockBean
    private DiscoveryClient discoveryClient;
    @SpyBean
    private ApiMetaRpcService apiMetaRpcService;

    @Test
    public void testUpload() throws Exception {
        String serviceId = "user";
        // mock start
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder().setAppName(serviceId).setIPAddr("192.168.15.10").setPort(8080).build();

        Application applicationMock = Mockito.mock(Application.class);
        Mockito.when(applicationMock.getInstances()).thenReturn(Arrays.asList(instanceInfo));

        Mockito.when(discoveryClient.getApplication(serviceId)).thenReturn(applicationMock);

        Map<String, ApiAccessMetaRespVO> apiAccessMapMock = new HashMap<>();

        ApiAccessMetaRespVO loginApiAccessMeta = ApiAccessMetaRespVO.builder()
                .authMeta(AuthMetaRespVO.builder().requiresUser(true).requiresPermissions(new String[0]).requiresRoles(new String[0]).build())
                .dataSecurityMeta(DataSecurityMetaRespVO.builder().requestDecrypt(true).responseEncrypt(true).sign(SignType.ALL.getType()).build())
                .repeatSubmitCheckMeta(RepeatSubmitCheckMetaRespVO.builder().check(true).expireMillis(10000L).build()).build();
        apiAccessMapMock.put("/user/api/login/checkPOST", loginApiAccessMeta);

        ApiAccessMetaRespVO registerApiAccessMeta = ApiAccessMetaRespVO.builder()
                .authMeta(AuthMetaRespVO.builder().requiresUser(false).requiresPermissions(new String[0]).requiresRoles(new String[0]).build())
                .dataSecurityMeta(DataSecurityMetaRespVO.builder().requestDecrypt(true).responseEncrypt(true).sign(SignType.ALL.getType()).build())
                .repeatSubmitCheckMeta(RepeatSubmitCheckMetaRespVO.builder().check(true).expireMillis(10000L).build()).build();
        apiAccessMapMock.put("/user/api/register/registerPOST", registerApiAccessMeta);
        RespVO<ApiMetaFetchRespVO> apiMetaFetchRespVOMock = new RespVO(new ApiMetaFetchRespVO(apiAccessMapMock));
        Mockito.doReturn(apiMetaFetchRespVOMock).when(apiMetaRpcService).fetchApiMeta(Mockito.anyString());
        // mock end

        RespVO<Base> result = post("/gateway/rpc/apiMeta/notifyFetch", new NotifyFetchReqVO(serviceId), new TypeReference<RespVO<Base>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
    }

}