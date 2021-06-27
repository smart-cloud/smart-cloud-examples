package org.smartframework.cloud.examples.basic.rpc.auth.response.base;

import org.smartframework.cloud.common.pojo.BaseEntityResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserRoleRelaBaseRespVO extends BaseEntityResponse {

	private static final long serialVersionUID = 1L;

    /** demo_user库t_user_info表id */
	private Long userInfoId;
	
    /** t_role_info表id */
	private Long roleInfoId;
	
}