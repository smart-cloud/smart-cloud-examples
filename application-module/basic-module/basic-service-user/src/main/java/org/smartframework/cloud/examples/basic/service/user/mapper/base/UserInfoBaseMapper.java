package org.smartframework.cloud.examples.basic.service.user.mapper.base;

import org.smartframework.cloud.examples.basic.service.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.UserInfoBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 用户信息base mapper
 *
 * @author liyulin
 * @date 2019-11-23
 */
public interface UserInfoBaseMapper extends ExtMapper<UserInfoEntity, UserInfoBaseRespBody, Long> {

}