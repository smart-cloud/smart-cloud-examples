package org.smartframework.cloud.examples.basic.auth.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
public class PermissionInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Test
    public void testCreate() throws Exception {
        PermissionCreateReqVO reqVO = new PermissionCreateReqVO();
        reqVO.setCode("/auth/oms/permission/create");
        reqVO.setDesc("创建权限");

        Response<Boolean> result = super.post("/auth/oms/permission/create", reqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

}