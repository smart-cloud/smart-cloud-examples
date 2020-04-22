package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(description = "上传权限信息")
public class GatewayAuthUploadReqVO extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;

}