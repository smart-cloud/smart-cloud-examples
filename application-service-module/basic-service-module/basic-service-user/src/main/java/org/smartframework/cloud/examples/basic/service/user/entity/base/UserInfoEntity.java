package org.smartframework.cloud.examples.basic.service.user.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2019-06-28
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_user_info")
public class UserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 手机号 */
	@Column(name = "f_mobile")
	private String mobile;

	/** 昵称 */
	@Column(name = "f_nick_name")
	private String nickname;

	/** 真实姓名 */
	@Column(name = "f_real_name")
	private String realname;

	/** 性别=={"1":"男","2":"女","3":"未知"} */
	@Column(name = "f_sex")
	private Byte sex;

	/** 出生年月 */
	@Column(name = "f_birthday")
	private Date birthday;

	/** 头像 */
	@Column(name = "f_profile_image")
	private String profileImage;

	/** 所在平台=={"1":"app","2":"web后台","3":"微信"} */
	@Column(name = "f_channel")
	private Byte channel;

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum Columns {
		/** 手机号 */
		MOBILE("mobile"),
		/** 昵称 */
		NICKNAME("nickname"),
		/** 真实姓名 */
		REALNAME("realname"),
		/** 性别 */
		SEX("sex"),
		/** 出生年月 */
		BIRTHDAY("birthday"),
		/** 头像 */
		PROFILE_IMAGE("profileImage"),
		/** 所在平台 */
		CHANNEL("channel");

		private String property;
	}

}