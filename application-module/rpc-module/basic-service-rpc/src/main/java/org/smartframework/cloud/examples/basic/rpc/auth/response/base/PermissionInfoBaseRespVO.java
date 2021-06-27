package org.smartframework.cloud.examples.basic.rpc.auth.response.base;

import org.smartframework.cloud.common.pojo.BaseEntityResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 权限表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class PermissionInfoBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** 权限编码 */
	private String code;
	
    /** 权限描述 */
	private String desc;
	
}