package org.smartframework.cloud.examples.basic.auth.service.oms;

import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RolePermissionOmsBiz;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.RolePermissionRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolePermissionOmsService {

    @Autowired
    private RolePermissionOmsBiz rolePermissionOmsBiz;

    /**
     * 添加角色权限
     *
     * @param req
     * @return
     */
    public Boolean create(RolePermissonCreateReqVO req) {
        return rolePermissionOmsBiz.create(req.getRoleId(), req.getPermissonIds(), UserContext.getUserId());
    }

    /**
     * 修改角色权限信息
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(RolePermissonUpdateReqVO req) {
        rolePermissionOmsBiz.logicDelete(req.getRoleId());
        rolePermissionOmsBiz.create(req.getRoleId(), req.getPermissonIds(), UserContext.getUserId());
        return true;
    }

    /**
     * 分页查询角色权限信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<RolePermissionRespVO> page(PageRolePermissonReqVO req) {
        return rolePermissionOmsBiz.page(req);
    }

}