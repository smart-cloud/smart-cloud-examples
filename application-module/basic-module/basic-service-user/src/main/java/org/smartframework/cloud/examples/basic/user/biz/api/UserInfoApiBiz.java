package org.smartframework.cloud.examples.basic.user.biz.api;

import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.UserInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import tk.mybatis.mapper.entity.Example;

@Repository
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

		userInfoBaseMapper.insertSelective(record);
		return record;
	}

	/**
	 * 判断改手机号是否已存在
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean existByMobile(String mobile) {
		Example example = new Example(UserInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(UserInfoEntity.Columns.mobile.toString(), mobile);
		return userInfoBaseMapper.selectCountByExample(example) > 0;
	}

}