package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.BasePageRequest;

/**
 * 分页查询角色权限信息请求参数
 *
 * @author collin
 * @date 2021-07-04
 */
@Getter
@Setter
public class PageRolePermissonReqVO extends BasePageRequest {

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