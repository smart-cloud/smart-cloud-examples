package org.smartframework.cloud.examples.basic.auth.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 角色表base mapper
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Mapper
public interface RoleInfoBaseMapper extends SmartMapper<RoleInfoEntity> {

}