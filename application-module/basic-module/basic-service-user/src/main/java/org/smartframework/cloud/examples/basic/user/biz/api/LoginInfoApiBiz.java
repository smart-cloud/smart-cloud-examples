package org.smartframework.cloud.examples.basic.user.biz.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.mapper.base.LoginInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;

@Repository
public class LoginInfoApiBiz extends BaseBiz<LoginInfoEntity> {

	@Autowired
	private LoginInfoBaseMapper loginInfoBaseMapper;

	/**
	 * 插入登陆信息
	 * 
	 * @param dto
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

		loginInfoBaseMapper.insertSelective(entity);
		return entity;
	}

	/**
	 * 根据用户名查询登陆信息
	 * 
	 * @param username
	 * @return
	 */
	public LoginInfoEntity queryByUsername(String username) {
		Example example = new Example(LoginInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(LoginInfoEntity.Columns.username.toString(), username);
		List<LoginInfoEntity> list = loginInfoBaseMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 判断该用户名是否已存在
	 * 
	 * @param username
	 * @return
	 */
	public boolean existByUsername(String username) {
		Example example = new Example(LoginInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(LoginInfoEntity.Columns.username.toString(), username);
		return loginInfoBaseMapper.selectCountByExample(example) > 0;
	}

}