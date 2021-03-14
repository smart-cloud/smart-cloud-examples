package org.smartframework.cloud.examples.basic.user.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.MaskLog;
import java.util.Date;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2021-03-14
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_user_info")
public class UserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
	
}