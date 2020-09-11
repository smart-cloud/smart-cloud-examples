package org.smartframework.cloud.examples.basic.rpc.user.request.api.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.user.UserInfoInsertReqVO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户注册请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RegisterUserReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

    /**
     * 用户信息
     */
    @NotNull
    @Valid
    private UserInfoInsertReqVO userInfo;

    /**
     * 登陆信息
     */
    @NotNull
    @Valid
    private LoginInfoInsertReqVO loginInfo;

}