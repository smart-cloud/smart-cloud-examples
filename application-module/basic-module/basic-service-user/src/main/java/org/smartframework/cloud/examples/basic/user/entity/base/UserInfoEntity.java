package org.smartframework.cloud.examples.basic.user.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2019-11-23
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_user_info")
public class UserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /** 手机号 */
    @MaskLog(MaskRule.MOBILE)
    @Column(name = "f_mobile")     
	private String mobile;
	
    /** 昵称 */
    @Column(name = "f_nick_name")     
	private String nickName;
	
    /** 真实姓名 */
    @MaskLog(MaskRule.NAME)
    @Column(name = "f_real_name")     
	private String realName;
	
    /** 性别=={\"1\":\"男\",\"2\":\"女\",\"3\":\"未知\"} */
    @Column(name = "f_sex")     
	private Byte sex;
	
    /** 出生年月 */
    @Column(name = "f_birthday")     
	private Date birthday;
	
    /** 头像 */
    @Column(name = "f_profile_image")     
	private String profileImage;
	
    /** 所在平台=={\"1\":\"app\",\"2\":\"web后台\",\"3\":\"微信\"} */
    @Column(name = "f_channel")     
	private Byte channel;
	
	/** 表字段名 */
	public enum Columns {
	    /** 手机号 */
		mobile,
	    /** 昵称 */
		nickName,
	    /** 真实姓名 */
		realName,
	    /** 性别=={\"1\":\"男\",\"2\":\"女\",\"3\":\"未知\"} */
		sex,
	    /** 出生年月 */
		birthday,
	    /** 头像 */
		profileImage,
	    /** 所在平台=={\"1\":\"app\",\"2\":\"web后台\",\"3\":\"微信\"} */
		channel;
	}

}