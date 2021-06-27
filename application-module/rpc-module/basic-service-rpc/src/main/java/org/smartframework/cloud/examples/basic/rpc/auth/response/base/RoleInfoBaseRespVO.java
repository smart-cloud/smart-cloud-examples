package org.smartframework.cloud.examples.basic.rpc.auth.response.base;

import org.smartframework.cloud.common.pojo.BaseEntityResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 角色表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class RoleInfoBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** 角色编码 */
	private String code;
	
    /** 角色描述 */
	private String desc;
	
}