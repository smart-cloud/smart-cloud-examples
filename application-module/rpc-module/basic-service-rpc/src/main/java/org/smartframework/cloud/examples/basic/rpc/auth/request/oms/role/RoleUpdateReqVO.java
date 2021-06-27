package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RoleUpdateReqVO extends Base {

    /**
     * 角色主键id
     */
    @NotNull
    private Long id;
    /**
     * 角色编码
     */
    @NotBlank
    private String code;
    /**
     * 角色描述
     */
    @NotBlank
    private String desc;

}