/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
class PermissionInfoOmsControllerIntegrationTest extends WebMvcIntegrationTest {

    @Autowired
    private PermissionInfoData permissionInfoData;

    @Test
    void testCreate() throws Exception {
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
    void testUpdate() throws Exception {
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
    void testDelete() throws Exception {
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
    void testPage() throws Exception {
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