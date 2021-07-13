package org.smartframework.cloud.examples.basic.auth.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.common.pojo.util.PageUtil;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.dataobject.PermissionDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.RoleDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.param.PageRolePermissonReqDO;
import org.smartframework.cloud.examples.basic.auth.dataobject.param.PermissionReqDO;
import org.smartframework.cloud.examples.basic.auth.entity.base.RolePermissionRelaEntity;
import org.smartframework.cloud.examples.basic.auth.mapper.base.RolePermissionRelaBaseMapper;
import org.smartframework.cloud.examples.basic.auth.mapper.oms.RolePermissionOmsMapper;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.PermissionInfoRespVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.RolePermissionRespVO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@DS(DataSourceName.BASIC_AUTH)
public class RolePermissionOmsBiz extends BaseBiz<RolePermissionRelaBaseMapper, RolePermissionRelaEntity> {

    @Autowired
    private RolePermissionOmsMapper rolePermissionOmsMapper;

    /**
     * 添加角色权限
     *
     * @param roleId
     * @param permissonIds
     * @param uid
     * @return
     */
    public Boolean create(Long roleId, Set<Long> permissonIds, Long uid) {
        Set<RolePermissionRelaEntity> rolePermissionRelaEntities = permissonIds.stream().map(permissonId -> {
            RolePermissionRelaEntity entity = super.create();
            entity.setRoleInfoId(roleId);
            entity.setPermissionInfoId(permissonId);
            entity.setInsertUser(uid);
            return entity;
        }).collect(Collectors.toSet());

        return super.saveBatch(rolePermissionRelaEntities);
    }

    /**
     * 逻辑删除角色权限
     *
     * @param roleId 权限主键id
     * @return
     */
    public Boolean logicDelete(Long roleId) {
        RolePermissionRelaEntity deletedEntity = new RolePermissionRelaEntity();
        deletedEntity.setDelUser(UserContext.getUserId());
        deletedEntity.setDelTime(new Date());
        deletedEntity.setDelState(DelState.DELETED);
        return super.update(deletedEntity, new LambdaQueryWrapper<RolePermissionRelaEntity>()
                .eq(RolePermissionRelaEntity::getRoleInfoId, roleId)
                .eq(RolePermissionRelaEntity::getDelState, DelState.NORMAL));
    }

    /**
     * 分页查询角色权限信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<RolePermissionRespVO> page(PageRolePermissonReqVO req) {
        long count = rolePermissionOmsMapper.countRole(req);
        List<RolePermissionRespVO> datas = null;
        if (count > 0) {
            PageRolePermissonReqDO pageRolePermissonReqDO = new PageRolePermissonReqDO();
            pageRolePermissonReqDO.setRoleCode(req.getRoleCode());
            pageRolePermissonReqDO.setRoleDesc(req.getRoleDesc());
            pageRolePermissonReqDO.setPermissonCode(req.getPermissonCode());
            pageRolePermissonReqDO.setPermissonDesc(req.getPermissonDesc());
            PageUtil.initLimitParams(pageRolePermissonReqDO, req);

            List<RoleDO> roles = rolePermissionOmsMapper.pageRole(pageRolePermissonReqDO);
            Set<Long> roleIds = roles.stream().map(RoleDO::getRoleId).collect(Collectors.toSet());

            PermissionReqDO permissionReqDO = new PermissionReqDO();
            permissionReqDO.setRoleIds(roleIds);
            permissionReqDO.setPermissonCode(req.getPermissonCode());
            permissionReqDO.setPermissonDesc(req.getPermissonDesc());
            List<PermissionDO> permissions = rolePermissionOmsMapper.listPermission(permissionReqDO);

            datas = roles.stream().map(role -> {
                RolePermissionRespVO respVO = new RolePermissionRespVO();
                respVO.setRoleId(role.getRoleId());
                respVO.setRoleCode(role.getRoleCode());
                respVO.setRoleDesc(role.getRoleDesc());
                respVO.setPermissions(convert(role.getRoleId(), permissions));
                return respVO;
            }).collect(Collectors.toList());
        }
        return new BasePageResponse<>(datas, req.getPageNum(), req.getPageSize(), count);
    }

    private Set<PermissionInfoRespVO> convert(Long roleId, List<PermissionDO> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return null;
        }

        return permissions.stream()
                .filter(permission -> permission.getRoleId().compareTo(roleId) == 0)
                .map(permission -> {
                    PermissionInfoRespVO respVO = new PermissionInfoRespVO();
                    respVO.setPermissonId(permission.getPermissonId());
                    respVO.setPermissonCode(permission.getPermissonCode());
                    respVO.setPermissonDesc(permission.getPermissonDesc());
                    return respVO;
                }).collect(Collectors.toSet());
    }

    /**
     * 查询角色拥有的所有权限id
     *
     * @param roleIds
     * @return
     */
    public Set<Long> listPermissionIds(Set<Long> roleIds) {
        List<RolePermissionRelaEntity> rolePermissionRelaEntities = super.list(new LambdaQueryWrapper<RolePermissionRelaEntity>()
                .select(RolePermissionRelaEntity::getPermissionInfoId)
                .in(RolePermissionRelaEntity::getRoleInfoId, roleIds)
                .eq(RolePermissionRelaEntity::getDelState, DelState.NORMAL));
        if (CollectionUtils.isEmpty(rolePermissionRelaEntities)) {
            return new HashSet<>(0);
        }

        return rolePermissionRelaEntities.stream()
                .map(RolePermissionRelaEntity::getPermissionInfoId)
                .collect(Collectors.toSet());
    }

}