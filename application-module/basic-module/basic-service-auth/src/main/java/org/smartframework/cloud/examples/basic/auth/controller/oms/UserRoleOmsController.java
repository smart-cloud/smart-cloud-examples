package org.smartframework.cloud.examples.basic.auth.controller.oms;

import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.api.core.annotation.auth.RequireRoles;
import org.smartframework.cloud.api.core.annotation.enums.Role;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.auth.service.oms.UserRoleOmsService;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("auth/oms/user/role")
@Validated
public class UserRoleOmsController {

    @Autowired
    private UserRoleOmsService userRoleOmsService;

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
        return RespUtil.success(userRoleOmsService.create(req));
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
        return RespUtil.success(userRoleOmsService.update(req));
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
        return RespUtil.success(userRoleOmsService.listRole(uid));
    }

}