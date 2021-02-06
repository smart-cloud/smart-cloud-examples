package org.smartframework.cloud.examples.basic.rpc.user.response.base;

import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.MaskLog;
import java.util.Date;
import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserInfoBaseRespVO extends BaseEntityRespVO {

	private static final long serialVersionUID = 1L;

	private Long id;
	
    /** 手机号 */
    @MaskLog(MaskRule.MOBILE)
	private String mobile;
	
    /** 昵称 */
	private String nickName;
	
    /** 真实姓名 */
    @MaskLog(MaskRule.NAME)
	private String realName;
	
    /** 性别=={"1":"男","2":"女","3":"未知"} */
	private Byte sex;
	
    /** 出生年月 */
	private Date birthday;
	
    /** 头像 */
	private String profileImage;
	
    /** 所在平台=={"1":"app","2":"web后台","3":"微信"} */
	private Byte channel;
	
    /** 新增时间 */
	private Date sysAddTime;
	
    /** 更新时间 */
	private Date sysUpdTime;
	
    /** 删除时间 */
	private Date sysDelTime;
	
    /** 新增者 */
	private Long sysAddUser;
	
    /** 修改者 */
	private Long sysUpdUser;
	
    /** 删除者 */
	private Long sysDelUser;
	
    /** 记录状态=={"1":"正常","2":"已删除"} */
	private Byte sysDelState;
	
}