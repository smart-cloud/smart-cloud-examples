package org.smartframework.cloud.examples.basic.auth.service.oms;

import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.UserRoleOmsBiz;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleOmsService {

    @Autowired
    private UserRoleOmsBiz userRoleOmsBiz;

    /**
     * 添加用户角色
     *
     * @param req
     * @return
     */
    public Boolean create(UserRoleCreateReqVO req) {
        return userRoleOmsBiz.create(req.getUid(), req.getRoleIds(), UserContext.getUserId());
    }

    /**
     * 修改用户角色信息
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(UserRoleUpdateReqVO req) {
        userRoleOmsBiz.logicDelete(req.getUid());
        userRoleOmsBiz.create(req.getUid(), req.getRoleIds(), UserContext.getUserId());
        return true;
    }

    /**
     * 查询用户所拥有的角色权限
     *
     * @param uid
     * @return
     */
    public List<UserRoleRespVO> listRole(Long uid) {
        return userRoleOmsBiz.listRole(uid);
    }


}