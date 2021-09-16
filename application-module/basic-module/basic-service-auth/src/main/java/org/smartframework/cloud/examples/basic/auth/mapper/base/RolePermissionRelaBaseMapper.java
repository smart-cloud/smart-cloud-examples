package org.smartframework.cloud.examples.basic.auth.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.auth.entity.base.RolePermissionRelaEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 角色权限关系表base mapper
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Mapper
public interface RolePermissionRelaBaseMapper extends SmartMapper<RolePermissionRelaEntity> {

}