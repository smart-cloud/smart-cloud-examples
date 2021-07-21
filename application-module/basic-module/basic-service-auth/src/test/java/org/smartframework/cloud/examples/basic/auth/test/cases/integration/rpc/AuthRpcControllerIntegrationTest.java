package org.smartframework.cloud.examples.basic.auth.test.cases.integration.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.starter.test.integration.WebMvcIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Rollback
@Transactional
class AuthRpcControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private PermissionInfoData prermissionInfoData;
    @Autowired
    private RoleInfoData roleInfoData;
    @Autowired
    private RolePermissionRelaData rolePermissionRelaData;
    @Autowired
    private UserRoleRelaData userRoleRelaData;

    @Test
    void testListByUid() throws Exception {
        Long uid = 1L;
        PermissionInfoEntity permissionInfoEntity = prermissionInfoData.insert();
        RoleInfoEntity roleInfoEntity = roleInfoData.insert();
        rolePermissionRelaData.insert(roleInfoEntity.getId(), Arrays.asList(permissionInfoEntity.getId()));
        userRoleRelaData.insert(uid, Arrays.asList(roleInfoEntity.getId()));

        Response<AuthRespDTO> result = super.get("/auth/rpc/auth/listByUid?uid=" + uid, null,
                new TypeReference<Response<AuthRespDTO>>() {
                });

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getHead()).isNotNull();
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getPermissions()).isNotEmpty();
        Assertions.assertThat(result.getBody().getRoles()).isNotEmpty();
    }

}