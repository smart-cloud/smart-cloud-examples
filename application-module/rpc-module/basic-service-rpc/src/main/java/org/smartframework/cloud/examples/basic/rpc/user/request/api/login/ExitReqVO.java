package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;

/**
 * @author liyulin
 * @date 2020-09-11
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ExitReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

}