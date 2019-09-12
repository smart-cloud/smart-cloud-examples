package org.smartframework.cloud.examples.basic.service.user.mapper.base;

import org.smartframework.cloud.examples.basic.service.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.service.rpc.user.response.base.LoginInfoBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 登录信息base mapper
 *
 * @author liyulin
 * @date 2019-07-15
 */
public interface LoginInfoBaseMapper extends ExtMapper<LoginInfoEntity, LoginInfoBaseRespBody, Long> {

}