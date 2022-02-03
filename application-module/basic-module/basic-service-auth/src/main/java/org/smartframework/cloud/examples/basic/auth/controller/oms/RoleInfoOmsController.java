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
package org.smartframework.cloud.examples.basic.auth.controller.oms;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.api.core.annotation.auth.RequireRoles;
import org.smartframework.cloud.api.core.annotation.enums.Role;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.oms.RoleInfoOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 角色信息
 *
 * @author collin
 * @date 2021-07-04
 * @status done
 */
@Validated
@RestController
@RequestMapping("auth/oms/role")
@RequiredArgsConstructor
public class RoleInfoOmsController {

    private final RoleInfoOmsService roleInfoOmsService;

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<Boolean> create(@RequestBody @Valid RoleCreateReqVO req) {
        return RespUtil.success(roleInfoOmsService.create(req));
    }

    /**
     * 修改角色信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:update")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> update(@RequestBody @Valid RoleUpdateReqVO req) {
        return RespUtil.success(roleInfoOmsService.update(req));
    }

    /**
     * 删除角色
     *
     * @param id 角色主键id
     * @return
     */
    @PostMapping("delete")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:delete")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> delete(@RequestBody @NotNull Long id) {
        return RespUtil.success(roleInfoOmsService.logicDelete(id));
    }

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    @GetMapping("page")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:page")
    @RequireTimestamp
    public Response<BasePageResponse<RoleInfoBaseRespVO>> page(@Valid @NotNull PageRoleReqVO req) {
        return RespUtil.success(roleInfoOmsService.page(req));
    }

}