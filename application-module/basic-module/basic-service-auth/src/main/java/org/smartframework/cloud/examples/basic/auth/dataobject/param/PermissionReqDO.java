package org.smartframework.cloud.examples.basic.auth.dataobject.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionReqDO extends Base {

    /**
     * 角色id
     */
    private Set<Long> roleIds;
    /**
     * 权限编码
     */
    private String permissonCode;
    /**
     * 权限描述
     */
    private String permissonDesc;

}