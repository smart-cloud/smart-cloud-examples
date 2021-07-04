package org.smartframework.cloud.examples.basic.auth.controller.oms;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.api.core.annotation.auth.RequireRoles;
import org.smartframework.cloud.api.core.enums.Role;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.oms.PermissionInfoOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PagePermissionReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.PermissionInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("auth/oms/permission")
@Validated
public class PermissionInfoOmsController {

    @Autowired
    private PermissionInfoOmsService permissionInfoOmsService;

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:create")
    @RequireDataSecurity
    @RequireRepeatSubmitCheck
    public Response<Boolean> create(@RequestBody @Valid PermissionCreateReqVO req) {
        return RespUtil.success(permissionInfoOmsService.create(req));
    }

    /**
     * 修改角色信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:update")
    @RequireDataSecurity
    public Response<Boolean> update(@RequestBody @Valid PermissionUpdateReqVO req) {
        return RespUtil.success(permissionInfoOmsService.update(req));
    }

    /**
     * 删除角色
     *
     * @param id 权限主键id
     * @return
     */
    @PostMapping("delete")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:delete")
    @RequireDataSecurity
    public Response<Boolean> delete(@RequestBody @NotNull Long id) {
        return RespUtil.success(permissionInfoOmsService.logicDelete(id));
    }

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    @GetMapping("page")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:permission:page")
    public Response<BasePageResponse<PermissionInfoBaseRespVO>> page(@Valid @NotNull PagePermissionReqVO req) {
        return RespUtil.success(permissionInfoOmsService.page(req));
    }

}