package org.smartframework.cloud.examples.basic.user.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.entity.BaseEntity;

import java.util.Date;

/**
 * 登录信息
 *
 * @author liyulin
 * @date 2021-03-14
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_login_info")
public class LoginInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @TableField(value = "t_user_id")
	private Long userId;
	
    /** 用户名 */
    @TableField(value = "f_username")
	private String username;
	
    /** 密码（md5加盐处理） */
    @MaskLog(MaskRule.PASSWROD)
    @TableField(value = "f_password")
	private String password;
	
    /** 16位盐值 */
    @MaskLog(MaskRule.DEFAULT)
    @TableField(value = "f_salt")
	private String salt;
	
    /** 最近成功登录时间 */
    @TableField(value = "f_last_login_time")
	private Date lastLoginTime;
	
    /** 密码状态=={"1":"未设置","2":"已设置"} */
    @TableField(value = "f_pwd_state")
	private Byte pwdState;
	
    /** 用户状态=={"1":"启用","2":"禁用"} */
    @TableField(value = "f_user_state")
	private Byte userState;
	
}