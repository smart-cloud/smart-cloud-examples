package org.smartframework.cloud.examples.basic.user.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 登录信息base mapper
 *
 * @author liyulin
 * @date 2021-03-23
 */
@Mapper
public interface LoginInfoBaseMapper extends SmartMapper<LoginInfoEntity> {

}