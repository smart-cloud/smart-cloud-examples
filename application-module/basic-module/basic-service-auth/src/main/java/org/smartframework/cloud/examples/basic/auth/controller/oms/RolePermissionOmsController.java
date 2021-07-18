package org.smartframework.cloud.examples.basic.auth.controller.oms;

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
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("auth/oms/role/permission")
@Validated
public class RolePermissionOmsController {

    @Autowired
    private RolePermissionOmsService rolePermissionOmsService;

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