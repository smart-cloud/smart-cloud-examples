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
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.test.core.integration.WebMvcIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.basic.auth.entity.RoleInfoEntity;
import org.smartframework.cloud.examples.basic.auth.test.data.RoleInfoData;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
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
        Assertions.assertThat(result.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS);
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
    }

}