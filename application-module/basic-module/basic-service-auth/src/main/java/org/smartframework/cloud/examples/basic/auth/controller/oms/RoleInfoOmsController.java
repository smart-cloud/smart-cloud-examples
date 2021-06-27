package org.smartframework.cloud.examples.basic.auth.controller.oms;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.api.core.annotation.auth.RequireRoles;
import org.smartframework.cloud.api.core.enums.Role;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.oms.RoleInfoOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("auth/oms/role")
@Validated
public class RoleInfoOmsController {

    @Autowired
    private RoleInfoOmsService roleInfoOmsService;

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
    public Response<Boolean> update(@RequestBody @Valid RoleUpdateReqVO req) {
        return RespUtil.success(roleInfoOmsService.update(req));
    }

    /**
     * 逻辑删除角色
     *
     * @param id 角色主键id
     * @return
     */
    @PostMapping("logicDelete")
    @RequireRoles(Role.ADMIN)
    @RequirePermissions("auth:role:logicDelete")
    @RequireDataSecurity
    public Response<Boolean> logicDelete(@RequestBody @NotNull Long id) {
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
    public Response<BasePageResponse<RoleInfoBaseRespVO>> page(@Valid @NotNull PageRoleReqVO req) {
        return RespUtil.success(roleInfoOmsService.page(req));
    }

}