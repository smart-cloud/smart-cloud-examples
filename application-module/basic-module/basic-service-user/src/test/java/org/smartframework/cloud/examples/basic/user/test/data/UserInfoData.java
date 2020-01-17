package org.smartframework.cloud.examples.basic.user.test.data;

import java.util.Date;

import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoData {

	@Autowired
	private UserInfoBaseMapper userInfoBaseMapper;

	public void insertTestData(Long id) {
		UserInfoEntity entity = new UserInfoEntity();
		entity.setId(id);
		entity.setMobile("18720912981");
		entity.setChannel(ChannelEnum.APP.getValue());
		entity.setSex(SexEnum.FEMALE.getValue());
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		userInfoBaseMapper.insertSelective(entity);
	}

}