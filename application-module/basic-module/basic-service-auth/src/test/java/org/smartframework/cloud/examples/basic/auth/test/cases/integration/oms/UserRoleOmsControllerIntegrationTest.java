package org.smartframework.cloud.examples.basic.auth.test.cases.integration.oms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.smartframework.cloud.examples.basic.auth.test.data.PermissionInfoData;
import org.smartframework.cloud.examples.basic.auth.test.data.RoleInfoData;
import org.smartframework.cloud.examples.basic.auth.test.data.RolePermissionRelaData;
import org.smartframework.cloud.examples.basic.auth.test.data.UserRoleRelaData;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Rollback
@Transactional
class UserRoleOmsControllerIntegrationTest extends WebMvcIntegrationTest {

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
        HashSet<Long> roleIds = Sets.newHashSet();
        roleIds.add(1L);
        roleIds.add(2L);
        roleIds.add(3L);

        UserRoleCreateReqVO userRoleCreateReqVO = new UserRoleCreateReqVO();
        userRoleCreateReqVO.setUid(1L);
        userRoleCreateReqVO.setRoleIds(roleIds);

        Response<Boolean> result = super.post("/auth/oms/user/role/create", userRoleCreateReqVO,
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
        HashSet<Long> roleIds = Sets.newHashSet();
        roleIds.add(1L);
        roleIds.add(2L);
        roleIds.add(3L);

        UserRoleUpdateReqVO userRoleUpdateReqVO = new UserRoleUpdateReqVO();
        userRoleUpdateReqVO.setUid(1L);
        userRoleUpdateReqVO.setRoleIds(roleIds);

        Response<Boolean> result = super.post("/auth/oms/user/role/update", userRoleUpdateReqVO,
                new TypeReference<Response<Boolean>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody()).isTrue();
    }

    @Test
    void testListRole() throws Exception {
        Long uid = 1L;
        PermissionInfoEntity permissionInfoEntity = prermissionInfoData.insert();
        RoleInfoEntity roleInfoEntity = roleInfoData.insert();
        rolePermissionRelaData.insert(roleInfoEntity.getId(), Arrays.asList(permissionInfoEntity.getId()));
        userRoleRelaData.insert(uid, Arrays.asList(roleInfoEntity.getId()));

        Response<List<UserRoleRespVO>> result = super.get("/auth/oms/user/role/listRole?uid=" + uid, null,
                new TypeReference<Response<List<UserRoleRespVO>>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotEmpty();
        Assertions.assertThat(result.getBody().get(0).getRoleCode()).isNotBlank();
        Assertions.assertThat(result.getBody().get(0).getPermissons()).isNotEmpty();
    }

}