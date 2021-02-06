package org.smartframework.cloud.examples.basic.user.mapper.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;

/**
 * 用户信息base mapper
 *
 * @author liyulin
 * @date 2021-02-09
 */
@DS(DataSourceName.BASIC_USER)
@Mapper
public interface UserInfoBaseMapper extends BaseMapper<UserInfoEntity> {

}