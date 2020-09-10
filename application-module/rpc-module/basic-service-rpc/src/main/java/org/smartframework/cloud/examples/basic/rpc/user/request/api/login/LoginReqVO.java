package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;

import javax.validation.constraints.NotBlank;

/**
 * 登陆请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class LoginReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    @MaskLog(MaskRule.PASSWROD)
    private String password;

}