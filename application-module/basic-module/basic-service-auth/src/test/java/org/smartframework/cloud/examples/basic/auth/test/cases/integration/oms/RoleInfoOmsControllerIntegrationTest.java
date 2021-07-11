package org.smartframework.cloud.examples.basic.auth.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.smartframework.cloud.examples.basic.auth.test.data.RoleInfoData;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class RoleInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private RoleInfoData roleInfoData;

    @Test
    void testCreate() throws Exception {
        RoleCreateReqVO reqVO = new RoleCreateReqVO();
        reqVO.setCode("admin");
        reqVO.setDesc("管理员");

        Response<Boolean> result = super.post("/auth/oms/role/create", reqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testUpdate() throws Exception {
        RoleInfoEntity entity = roleInfoData.insert();
        RoleUpdateReqVO reqVO = new RoleUpdateReqVO();
        reqVO.setId(entity.getId());
        reqVO.setCode("pm");
        reqVO.setDesc("项目经理");

        Response<Boolean> result = super.post("/auth/oms/role/update", reqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testDelete() throws Exception {
        RoleInfoEntity entity = roleInfoData.insert();
        Long id = entity.getId();
        Response<Boolean> result = super.post("/auth/oms/role/delete", id,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testPage() throws Exception {
        RoleInfoEntity entity = roleInfoData.insert();
        PageRoleReqVO reqVO = new PageRoleReqVO();
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        reqVO.setCode(entity.getCode());
        reqVO.setDesc(entity.getDescription());

        Response<BasePageResponse<RoleInfoBaseRespVO>> result = super.get("/auth/oms/role/page", reqVO,
                new TypeReference<Response<BasePageResponse<RoleInfoBaseRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}