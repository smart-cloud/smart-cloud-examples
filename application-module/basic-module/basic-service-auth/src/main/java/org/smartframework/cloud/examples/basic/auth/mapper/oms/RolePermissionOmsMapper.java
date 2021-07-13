package org.smartframework.cloud.examples.basic.auth.mapper.oms;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.auth.dataobject.PermissionDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.RoleDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.param.PageRolePermissonReqDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.param.PermissionReqDO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;

import java.util.List;

@Mapper
public interface RolePermissionOmsMapper {

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    List<RoleDO> pageRole(PageRolePermissonReqDO req);

    /**
     * 查询满足条件的角色权限总记录数
     *
     * @param req
     * @return
     */
    long countRole(PageRolePermissonReqVO req);

    /**
     * 查询角色对应的所有权限
     *
     * @param reqDO
     * @return
     */
    List<PermissionDO> listPermission(PermissionReqDO reqDO);

}