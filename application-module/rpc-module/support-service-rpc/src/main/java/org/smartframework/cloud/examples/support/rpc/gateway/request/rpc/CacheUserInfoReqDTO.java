package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CacheUserInfoReqDTO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 真实姓名
     */
    @NotBlank
    @MaskLog(MaskRule.NAME)
    private String realName;

    /**
     * 手机号
     */
    @MaskLog(MaskRule.MOBILE)
    private String mobile;

    /**
     * 用户所拥有的角色
     */
    private Set<String> roles;
    /**
     * 用户所拥有的权限
     */
    private Set<String> permissions;

}