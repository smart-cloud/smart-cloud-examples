package org.smartframework.cloud.examples.basic.auth.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.smartframework.cloud.examples.basic.auth.test.data.PermissionInfoData;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PagePermissionReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.PermissionInfoBaseRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

//@Rollback
//@Transactional
public class PermissionInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private PermissionInfoData permissionInfoData;

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

    @Test
    public void testUpdate() throws Exception {
        PermissionInfoEntity entity = permissionInfoData.insert();
        PermissionUpdateReqVO reqVO = new PermissionUpdateReqVO();
        reqVO.setId(entity.getId());
        reqVO.setCode("/auth/oms/permission/update");
        reqVO.setDesc("修改权限");

        Response<Boolean> result = super.post("/auth/oms/permission/update", reqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    public void testDelete() throws Exception {
        PermissionInfoEntity entity = permissionInfoData.insert();
        Long id = entity.getId();
        Response<Boolean> result = super.post("/auth/oms/permission/delete", id,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    public void testPage() throws Exception {
        PermissionInfoEntity entity = permissionInfoData.insert();
        PagePermissionReqVO reqVO = new PagePermissionReqVO();
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        reqVO.setCode(entity.getCode());
        reqVO.setDesc(entity.getDescription());

        Response<BasePageResponse<PermissionInfoBaseRespVO>> result = super.get("/auth/oms/permission/page", reqVO,
                new TypeReference<Response<BasePageResponse<PermissionInfoBaseRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}