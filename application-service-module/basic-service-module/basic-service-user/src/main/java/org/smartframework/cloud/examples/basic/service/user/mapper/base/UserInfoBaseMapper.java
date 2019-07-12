package org.smartframework.cloud.examples.basic.service.user.mapper.base;

import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.UserInfoBaseRespBody;
import org.smartframework.cloud.examples.basic.service.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

public interface UserInfoBaseMapper extends ExtMapper<UserInfoEntity, UserInfoBaseRespBody, Long> {

}