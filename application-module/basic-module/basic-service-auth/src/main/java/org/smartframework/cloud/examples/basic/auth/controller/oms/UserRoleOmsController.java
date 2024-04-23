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
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.auth.service.oms.UserRoleOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户角色信息
 *
 * @author collin
 * @date 2021-07-04
 * @status done
 */
@Validated
@RestController
@RequestMapping("auth/oms/user/role")
@RequiredArgsConstructor
public class UserRoleOmsController {

    private final UserRoleOmsService userRoleOmsService;

    /**
     * 添加用户角色
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:user:role:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    @RequireTimestamp
    public Response<Boolean> create(@RequestBody @Valid UserRoleCreateReqVO req) {
        return ResponseUtil.success(userRoleOmsService.create(req));
    }

    /**
     * 修改用户角色信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:user:role:update")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> update(@RequestBody @Valid UserRoleUpdateReqVO req) {
        return ResponseUtil.success(userRoleOmsService.update(req));
    }

    /**
     * 查询用户所拥有的角色权限
     *
     * @param uid
     * @return
     */
    @GetMapping("listRole")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:user:role:listRole")
    @RequireTimestamp
    public Response<List<UserRoleRespVO>> listRole(@NotNull Long uid) {
        return ResponseUtil.success(userRoleOmsService.listRole(uid));
    }

}