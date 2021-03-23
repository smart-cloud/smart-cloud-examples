package org.smartframework.cloud.examples.basic.user.biz.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@DS(DataSourceName.BASIC_USER)
public class UserInfoApiBiz extends BaseBiz<UserInfoEntity> {

    @Autowired
    @Getter
    private UserInfoBaseMapper userInfoBaseMapper;

    /**
     * 插入用户信息
     *
     * @param userInfo
     * @return
     */
    public UserInfoEntity insert(UserInfoInsertReqVO userInfo) {
        UserInfoEntity record = create();
        record.setMobile(userInfo.getMobile());
        record.setNickName(userInfo.getNickname());
        record.setRealName(userInfo.getRealname());
        record.setSex(userInfo.getSex());
        record.setBirthday(userInfo.getBirthday());
        record.setProfileImage(userInfo.getProfileImage());
        record.setChannel(userInfo.getChannel());
        userInfoBaseMapper.insert(record);

        return record;
    }

    /**
     * 判断改手机号是否已存在
     *
     * @param mobile
     * @return
     */
    public boolean existByMobile(String mobile) {
        LambdaQueryWrapper<UserInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserInfoEntity::getId).eq(UserInfoEntity::getMobile, mobile);

        return userInfoBaseMapper.selectOne(wrapper) != null;
    }

}