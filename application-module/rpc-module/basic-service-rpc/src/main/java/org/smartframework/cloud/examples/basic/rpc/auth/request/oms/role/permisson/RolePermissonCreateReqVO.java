package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RolePermissonCreateReqVO extends Base {

    /**
     * 角色id
     */
    @NotNull
    private Long roleId;
    /**
     * 权限id
     */
    @NotEmpty
    private Set<Long> permissonIds;

}