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
 * 登录信息
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoBaseRespVO extends BaseEntityRespVO {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	
    /** 用户名 */
	private String username;
	
    /** 密码（md5加盐处理） */
    @MaskLog(MaskRule.PASSWROD)
	private String password;
	
    /** 16位盐值 */
    @MaskLog(MaskRule.DEFAULT)
	private String salt;
	
    /** 最近成功登录时间 */
	private Date lastLoginTime;
	
    /** 密码状态=={"1":"未设置","2":"已设置"} */
	private Byte pwdState;
	
    /** 用户状态=={"1":"启用","2":"禁用"} */
	private Byte userState;
	
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