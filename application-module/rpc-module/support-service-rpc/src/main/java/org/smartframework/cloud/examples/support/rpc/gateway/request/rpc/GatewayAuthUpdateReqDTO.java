package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 修改权限信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GatewayAuthUpdateReqDTO extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;

}