package org.smartframework.cloud.examples.basic.rpc.user.response.api.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;

/**
 * 用户注册响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RegisterUserRespVO extends LoginRespVO {

    private static final long serialVersionUID = 1L;

    public RegisterUserRespVO(LoginRespVO loginRespVO) {
        super(loginRespVO.getUserId());
    }

}