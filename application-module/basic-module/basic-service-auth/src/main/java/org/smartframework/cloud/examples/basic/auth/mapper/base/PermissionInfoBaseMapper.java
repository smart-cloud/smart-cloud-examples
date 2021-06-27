package org.smartframework.cloud.examples.basic.auth.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.SmartMapper;

/**
 * 权限表base mapper
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Mapper
public interface PermissionInfoBaseMapper extends SmartMapper<PermissionInfoEntity> {

}