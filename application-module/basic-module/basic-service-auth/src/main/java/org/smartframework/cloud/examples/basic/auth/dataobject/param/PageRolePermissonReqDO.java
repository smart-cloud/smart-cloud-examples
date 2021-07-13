package org.smartframework.cloud.examples.basic.auth.dataobject.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.dataobject.BasePageRequestDO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRolePermissonReqDO extends BasePageRequestDO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 权限编码
     */
    private String permissonCode;
    /**
     * 权限描述
     */
    private String permissonDesc;

}