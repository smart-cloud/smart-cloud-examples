package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.smartframework.cloud.common.pojo.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Api访问控制元数据信息")
public class ApiMetaUploadReqVO extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Api访问控制信息<url编码（由url+http method组成）, Api访问控制信息>")
	@NotEmpty
	private Map<String, ApiAC> apiACs;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@ApiModel(description = "Api访问控制信息")
	public static class ApiAC extends Base {

		private static final long serialVersionUID = 1L;

		/** 是否需要token校验 */
		private boolean tokenCheck;

		/** 签名控制 */
		private byte sign;

		/** 请求参数是否需要解密 */
		private boolean decrypt;

		/** 响应信息是否需要加密 */
		private boolean encrypt;

		/** 是否需要权限控制 */
		private boolean auth;

		/** 是否需要重复提交校验 */
		private boolean repeatSubmitCheck;
	}

}