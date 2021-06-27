package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson;

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
public class PermissionCreateReqVO extends Base {

    /**
     * 权限编码
     */
    @NotBlank
    private String code;
    /**
     * 权限描述
     */
    @NotBlank
    private String desc;

}