package org.smartframework.cloud.examples.basic.rpc.user.response.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;

import java.util.Date;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserInfoBaseRespVO extends BaseEntityRespVO {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @MaskLog(MaskRule.MOBILE)
    private String mobile;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    @MaskLog(MaskRule.NAME)
    private String realName;

    /**
     * 性别=={"1":"男","2":"女","3":"未知"}
     */
    private Byte sex;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 头像
     */
    private String profileImage;

    /**
     * 所在平台=={"1":"app","2":"web后台","3":"微信"}
     */
    private Byte channel;

}