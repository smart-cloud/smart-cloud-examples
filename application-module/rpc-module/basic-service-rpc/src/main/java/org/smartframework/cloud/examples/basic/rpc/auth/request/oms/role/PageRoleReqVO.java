package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.BasePageRequest;

/**
 * 分页查询角色信息请求参数
 *
 * @author collin
 * @date 2021-07-04
 */
@Getter
@Setter
public class PageRoleReqVO extends BasePageRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色描述
     */
    private String desc;

}