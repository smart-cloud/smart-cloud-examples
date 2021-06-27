package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role;

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
public class UserRoleCreateReqVO extends Base {

    /**
     * 用户id
     */
    @NotNull
    private Long uid;
    /**
     * 角色id
     */
    @NotEmpty
    private Set<Long> roleIds;

}