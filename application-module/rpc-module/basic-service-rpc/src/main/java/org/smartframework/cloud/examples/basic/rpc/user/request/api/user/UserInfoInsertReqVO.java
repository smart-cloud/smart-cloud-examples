package org.smartframework.cloud.examples.basic.rpc.user.request.api.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 添加用户请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserInfoInsertReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Size(max = 11)
    @NotBlank
    @MaskLog(MaskRule.PASSWROD)
    private String mobile;

    /**
     * 昵称
     */
    @Size(max = 45)
    private String nickname;

    /**
     * 真实姓名
     */
    @Size(max = 45)
    @MaskLog(MaskRule.NAME)
    private String realname;

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
    @Size(max = 255)
    private String profileImage;

    /**
     * 所在平台=={"1":"app","2":"web后台","3":"微信"}
     */
    @NotNull
    private Byte channel;

}