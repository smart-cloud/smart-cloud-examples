package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

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

/**
 * 添加登陆信息请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Size(min = 6, max = 20)
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @Size(min = 6, max = 45)
    @NotBlank
    @MaskLog(MaskRule.PASSWROD)
    private String password;

    /**
     * 密码状态=={"1":"未设置","2":"已设置"}
     */
    @NotNull
    private Byte pwdState;

}