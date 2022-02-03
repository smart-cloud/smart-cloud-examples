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
import org.smartframework.cloud.examples.basic.auth.service.oms.RolePermissionOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.RolePermissionRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 角色权限信息
 *
 * @author collin
 * @date 2021-07-04
 * @status done
 */
@Validated
@RestController
@RequestMapping("auth/oms/role/permission")
@RequiredArgsConstructor
public class RolePermissionOmsController {

    private final RolePermissionOmsService rolePermissionOmsService;

    /**
     * 添加角色权限
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:permission:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<Boolean> create(@RequestBody @Valid RolePermissonCreateReqVO req) {
        return RespUtil.success(rolePermissionOmsService.create(req));
    }

    /**
     * 修改角色权限信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:permission:update")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> update(@RequestBody @Valid RolePermissonUpdateReqVO req) {
        return RespUtil.success(rolePermissionOmsService.update(req));
    }

    /**
     * 分页查询角色权限信息
     *
     * @param req
     * @return
     */
    @GetMapping("page")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:permission:page")
    @RequireTimestamp
    public Response<BasePageResponse<RolePermissionRespVO>> page(@Valid @NotNull PageRolePermissonReqVO req) {
        return RespUtil.success(rolePermissionOmsService.page(req));
    }

}