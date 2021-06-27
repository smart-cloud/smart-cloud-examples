package org.smartframework.cloud.examples.basic.auth.dataobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDO extends Base {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;

}
