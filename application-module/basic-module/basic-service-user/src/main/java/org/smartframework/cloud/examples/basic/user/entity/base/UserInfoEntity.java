package org.smartframework.cloud.examples.basic.user.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

import java.util.Date;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_user_info")
public class UserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "f_id")
	private Long id;
	
    /** 手机号 */
    @MaskLog(MaskRule.MOBILE)
    @TableField(value = "f_mobile")
	private String mobile;
	
    /** 昵称 */
    @TableField(value = "f_nick_name")
	private String nickName;
	
    /** 真实姓名 */
    @MaskLog(MaskRule.NAME)
    @TableField(value = "f_real_name")
	private String realName;
	
    /** 性别=={"1":"男","2":"女","3":"未知"} */
    @TableField(value = "f_sex")
	private Byte sex;
	
    /** 出生年月 */
    @TableField(value = "f_birthday")
	private Date birthday;
	
    /** 头像 */
    @TableField(value = "f_profile_image")
	private String profileImage;
	
    /** 所在平台=={"1":"app","2":"web后台","3":"微信"} */
    @TableField(value = "f_channel")
	private Byte channel;
	
    /** 新增时间 */
    @TableField(value = "f_sys_add_time")
	private Date sysAddTime;
	
    /** 更新时间 */
    @TableField(value = "f_sys_upd_time")
	private Date sysUpdTime;
	
    /** 删除时间 */
    @TableField(value = "f_sys_del_time")
	private Date sysDelTime;
	
    /** 新增者 */
    @TableField(value = "f_sys_add_user")
	private Long sysAddUser;
	
    /** 修改者 */
    @TableField(value = "f_sys_upd_user")
	private Long sysUpdUser;
	
    /** 删除者 */
    @TableField(value = "f_sys_del_user")
	private Long sysDelUser;
	
    /** 记录状态=={"1":"正常","2":"已删除"} */
    @TableField(value = "f_sys_del_state")
	private Byte sysDelState;
	
}