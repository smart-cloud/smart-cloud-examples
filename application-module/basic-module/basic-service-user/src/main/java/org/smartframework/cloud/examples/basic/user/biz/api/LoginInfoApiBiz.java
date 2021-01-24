package org.smartframework.cloud.examples.basic.user.biz.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.LoginInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class LoginInfoApiBiz extends BaseBiz<LoginInfoEntity> {

    @Autowired
    private LoginInfoBaseMapper loginInfoBaseMapper;

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
        loginInfoBaseMapper.insert(entity);
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

        return loginInfoBaseMapper.selectOne(wrapper);
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

        return loginInfoBaseMapper.selectOne(wrapper) != null;
    }

}