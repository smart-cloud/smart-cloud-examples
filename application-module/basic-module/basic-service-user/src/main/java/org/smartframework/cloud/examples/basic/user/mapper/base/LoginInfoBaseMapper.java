package org.smartframework.cloud.examples.basic.user.mapper.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;

/**
 * 登录信息base mapper
 *
 * @author liyulin
 * @date 2020-01-17
 */
@DS(DataSourceName.BASIC_USER)
@Mapper
public interface LoginInfoBaseMapper extends BaseMapper<LoginInfoEntity> {

}