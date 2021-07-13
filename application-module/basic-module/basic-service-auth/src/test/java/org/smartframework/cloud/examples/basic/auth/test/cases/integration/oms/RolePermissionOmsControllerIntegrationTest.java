package org.smartframework.cloud.examples.basic.auth.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.smartframework.cloud.examples.basic.auth.test.data.PermissionInfoData;
import org.smartframework.cloud.examples.basic.auth.test.data.RoleInfoData;
import org.smartframework.cloud.examples.basic.auth.test.data.RolePermissionRelaData;
import org.smartframework.cloud.examples.basic.auth.test.data.UserRoleRelaData;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.RolePermissionRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@Rollback
@Transactional
class RolePermissionOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private PermissionInfoData prermissionInfoData;
    @Autowired
    private RoleInfoData roleInfoData;
    @Autowired
    private RolePermissionRelaData rolePermissionRelaData;
    @Autowired
    private UserRoleRelaData userRoleRelaData;

    @Test
    void testCreate() throws Exception {
        HashSet<Long> permissonIds = Sets.newHashSet();
        permissonIds.add(1L);
        permissonIds.add(2L);
        permissonIds.add(3L);

        RolePermissonCreateReqVO rolePermissonCreateReqVO = new RolePermissonCreateReqVO();
        rolePermissonCreateReqVO.setRoleId(1L);
        rolePermissonCreateReqVO.setPermissonIds(permissonIds);

        Response<Boolean> result = super.post("/auth/oms/role/permission/create", rolePermissonCreateReqVO,
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
        HashSet<Long> permissonIds = Sets.newHashSet();
        permissonIds.add(1L);
        permissonIds.add(2L);
        permissonIds.add(3L);

        RolePermissonUpdateReqVO reqVO = new RolePermissonUpdateReqVO();
        reqVO.setRoleId(1L);
        reqVO.setPermissonIds(permissonIds);


        Response<Boolean> result = super.post("/auth/oms/role/permission/update", reqVO,
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
        Long uid = 1L;
        PermissionInfoEntity permissionInfoEntity = prermissionInfoData.insert();
        RoleInfoEntity roleInfoEntity = roleInfoData.insert();
        rolePermissionRelaData.insert(roleInfoEntity.getId(), Arrays.asList(permissionInfoEntity.getId()));
        userRoleRelaData.insert(uid, Arrays.asList(roleInfoEntity.getId()));

        PageRolePermissonReqVO reqVO = new PageRolePermissonReqVO();
        reqVO.setRoleCode(roleInfoEntity.getCode());
        reqVO.setRoleDesc(roleInfoEntity.getDescription());
        reqVO.setPermissonCode(permissionInfoEntity.getCode());
        reqVO.setPermissonDesc(permissionInfoEntity.getDescription());
        reqVO.setPageNum(1);
        reqVO.setPageSize(10);

        Response<BasePageResponse<RolePermissionRespVO>> result = super.get("/auth/oms/role/permission/page", reqVO,
                new TypeReference<Response<BasePageResponse<RolePermissionRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}