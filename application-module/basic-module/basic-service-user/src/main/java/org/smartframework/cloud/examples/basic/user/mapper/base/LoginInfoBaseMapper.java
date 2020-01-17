package org.smartframework.cloud.examples.basic.user.mapper.base;

import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.LoginInfoBaseRespVO;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

/**
 * 登录信息base mapper
 *
 * @author liyulin
 * @date 2020-01-17
 */
public interface LoginInfoBaseMapper extends ExtMapper<LoginInfoEntity, LoginInfoBaseRespVO, Long> {

}