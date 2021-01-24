package org.smartframework.cloud.examples.app.auth.core;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBO implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    @NotBlank
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

}