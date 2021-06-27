package org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 权限信息
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class PermissionInfoRespVO extends Base {

    private static final long serialVersionUID = 1L;

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