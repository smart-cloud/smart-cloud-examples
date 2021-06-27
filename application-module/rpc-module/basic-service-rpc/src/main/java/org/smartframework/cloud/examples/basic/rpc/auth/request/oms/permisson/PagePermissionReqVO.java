package org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.BasePageRequest;

/**
 * 分页查询权限信息请求参数
 *
 * @author collin
 * @date 2021-07-04
 */
@Getter
@Setter
public class PagePermissionReqVO extends BasePageRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 权限编码
     */
    private String code;
    /**
     * 权限描述
     */
    private String desc;

}