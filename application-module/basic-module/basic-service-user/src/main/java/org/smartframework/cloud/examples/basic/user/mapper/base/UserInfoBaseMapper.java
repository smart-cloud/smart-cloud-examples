package org.smartframework.cloud.examples.basic.user.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.SmartMapper;

/**
 * 用户信息base mapper
 *
 * @author liyulin
 * @date 2021-03-23
 */
@Mapper
public interface UserInfoBaseMapper extends SmartMapper<UserInfoEntity> {

}