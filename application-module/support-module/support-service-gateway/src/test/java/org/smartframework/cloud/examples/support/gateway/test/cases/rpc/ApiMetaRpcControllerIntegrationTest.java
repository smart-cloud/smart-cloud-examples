package org.smartframework.cloud.examples.support.gateway.test.cases.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.enums.ReturnCodeEnum;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.test.integration.WebReactiveIntegrationTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyulin
 * @date 2020-09-12
 */
public class ApiMetaRpcControllerIntegrationTest extends WebReactiveIntegrationTest {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void testUpload() throws Exception {
        String serviceId = "user";
        // mock start
        DefaultServiceInstance instanceMock = new DefaultServiceInstance(serviceId, serviceId, "192.168.15.10", 8080, false);
        Mockito.when(loadBalancerClient.choose(ArgumentMatchers.any())).thenReturn(instanceMock);


        Map<String, ApiMetaFetchRespVO.ApiAC> apiACsMock = new HashMap<>();
        apiACsMock.put("/user/api/login/checkPOST", ApiMetaFetchRespVO.ApiAC.builder().auth(false).tokenCheck(true).decrypt(false).build());
        apiACsMock.put("/user/api/register/registerPOST", ApiMetaFetchRespVO.ApiAC.builder().auth(false).tokenCheck(true).decrypt(false).build());

        Mockito.when(restTemplate.exchange(ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity(RespUtil.success(new ApiMetaFetchRespVO(apiACsMock)), HttpStatus.OK));
        // mock end

        RespVO<Base> result = post("/gateway/rpc/apiMeta/notifyFetch", new NotifyFetchReqVO(serviceId), new TypeReference<RespVO<Base>>() {
        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
    }

}