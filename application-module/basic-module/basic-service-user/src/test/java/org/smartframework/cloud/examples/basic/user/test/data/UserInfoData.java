package org.smartframework.cloud.examples.basic.user.test.data;

import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.constants.DelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserInfoData {

	@Autowired
	private UserInfoBaseMapper userInfoBaseMapper;

	public void insertTestData(Long id) {
		UserInfoEntity entity = new UserInfoEntity();
		entity.setId(id);
		entity.setMobile("18720912981");
		entity.setRealName("李四");
		entity.setChannel(ChannelEnum.APP.getValue());
		entity.setSex(SexEnum.FEMALE.getValue());
		entity.setInsertTime(new Date());
		entity.setDelState(DelState.NORMAL);
		userInfoBaseMapper.insert(entity);
	}

}