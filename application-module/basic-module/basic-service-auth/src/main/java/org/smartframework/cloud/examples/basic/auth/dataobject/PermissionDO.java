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
public class PermissionDO extends Base {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long permissonId;
    /**
     * 权限编码
     */
    private String permissonCode;
    /**
     * 权限描述
     */
    private String permissonDesc;

}