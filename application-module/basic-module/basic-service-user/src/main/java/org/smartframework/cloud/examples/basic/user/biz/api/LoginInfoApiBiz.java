package org.smartframework.cloud.examples.basic.user.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.LoginInfoBaseMapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@DS(DataSourceName.BASIC_USER)
public class LoginInfoApiBiz extends BaseBiz<LoginInfoBaseMapper, LoginInfoEntity> {

    /**
     * 插入登陆信息
     *
     * @param bo
     * @return
     */
    public LoginInfoEntity insert(LoginInfoInsertBizBO bo) {
        LoginInfoEntity entity = create();
        entity.setUserId(bo.getUserId());
        entity.setUsername(bo.getUsername());
        entity.setSalt(bo.getSalt());
        entity.setPassword(bo.getPassword());
        entity.setPwdState(bo.getPwdState());
        entity.setLastLoginTime(new Date());
        entity.setUserState(UserStateEnum.ENABLE.getValue());
        super.save(entity);
        return entity;
    }

    /**
     * 根据用户名查询登陆信息
     *
     * @param username
     * @return
     */
    public LoginInfoEntity queryByUsername(String username) {
        LambdaQueryWrapper<LoginInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoginInfoEntity::getUsername, username);

        return super.getOne(wrapper);
    }

    /**
     * 判断该用户名是否已存在
     *
     * @param username
     * @return
     */
    public boolean existByUsername(String username) {
        LambdaQueryWrapper<LoginInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(LoginInfoEntity::getId).eq(LoginInfoEntity::getUsername, username);

        return super.getOne(wrapper) != null;
    }

}