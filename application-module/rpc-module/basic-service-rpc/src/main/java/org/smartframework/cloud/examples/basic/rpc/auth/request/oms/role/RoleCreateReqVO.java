package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RoleCreateReqVO extends Base {

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