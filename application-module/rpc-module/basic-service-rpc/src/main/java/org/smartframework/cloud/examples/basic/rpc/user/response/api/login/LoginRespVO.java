package org.smartframework.cloud.examples.basic.rpc.user.response.api.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 登陆响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LoginRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

}