package org.smartframework.cloud.examples.basic.user.mapper.base;

import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 用户信息base mapper
 *
 * @author liyulin
 * @date 2020-01-17
 */
public interface UserInfoBaseMapper extends ExtMapper<UserInfoEntity, UserInfoBaseRespVO, Long> {

}