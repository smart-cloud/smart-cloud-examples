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

import io.github.smart.cloud.api.core.annotation.RequireDataSecurity;
import io.github.smart.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import io.github.smart.cloud.api.core.annotation.RequireTimestamp;
import io.github.smart.cloud.api.core.annotation.auth.RequirePermissions;
import io.github.smart.cloud.api.core.annotation.auth.RequireRoles;
import io.github.smart.cloud.api.core.annotation.constants.Role;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.auth.service.oms.PermissionInfoOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PagePermissionReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.PermissionInfoBaseRespVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 权限信息
 *
 * @author collin
 * @date 2021-07-04
 * @status done
 */
@Validated
@RestController
@RequestMapping("auth/oms/permission")
@RequiredArgsConstructor
public class PermissionInfoOmsController {

    private final PermissionInfoOmsService permissionInfoOmsService;

    /**
     * 添加权限
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<Boolean> create(@RequestBody @Valid PermissionCreateReqVO req) {
        return ResponseUtil.success(permissionInfoOmsService.create(req));
    }

    /**
     * 修改权限信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:update")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> update(@RequestBody @Valid PermissionUpdateReqVO req) {
        return ResponseUtil.success(permissionInfoOmsService.update(req));
    }

    /**
     * 删除权限
     *
     * @param id 权限主键id
     * @return
     */
    @PostMapping("delete")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:delete")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> delete(@RequestBody @NotNull Long id) {
        return ResponseUtil.success(permissionInfoOmsService.logicDelete(id));
    }

    /**
     * 分页查询权限信息
     *
     * @param req
     * @return
     */
    @GetMapping("page")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:page")
    @RequireTimestamp
    public Response<BasePageResponse<PermissionInfoBaseRespVO>> page(@Valid @NotNull PagePermissionReqVO req) {
        return ResponseUtil.success(permissionInfoOmsService.page(req));
    }

}