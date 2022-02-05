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
package org.smartframework.cloud.examples.basic.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.MaskLog;
import java.util.Date;
import org.smartframework.cloud.starter.mybatis.plus.common.entity.BaseEntity;

/**
 * 登录信息
 *
 * @author collin
 * @date 2021-12-12
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