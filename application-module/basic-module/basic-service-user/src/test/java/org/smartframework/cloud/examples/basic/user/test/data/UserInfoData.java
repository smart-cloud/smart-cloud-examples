/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.basic.user.test.data;

import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.smartframework.cloud.examples.basic.rpc.enums.user.ChannelEnum;
import org.smartframework.cloud.examples.basic.rpc.enums.user.SexEnum;
import org.smartframework.cloud.examples.basic.user.entity.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
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
		entity.setDelState(DeleteState.NORMAL);
		userInfoBaseMapper.insert(entity);
	}

}