package org.smartframework.cloud.examples.support.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstant;
import org.smartframework.cloud.starter.core.business.security.enums.ApiUseSideEnum;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 网关过滤器
 *
 * @author liyulin
 * @date 2020-05-13
 */
@Component
public class GatewayFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		processRpcContentType(exchange.getRequest(), exchange.getResponse());
		return chain.filter(exchange);
	}

	/**
	 * rpc接口content-type设置
	 * 
	 * @param request
	 * @param response
	 */
	private void processRpcContentType(ServerHttpRequest request, ServerHttpResponse response) {
		String path = request.getURI().getPath();
		if (!path.contains(ApiUseSideEnum.RPC.getPathSegment())) {
			return;
		}

		MediaType mediaType = response.getHeaders().getContentType();
		if (mediaType == null || StringUtils.isBlank(mediaType.getType())) {
			response.getHeaders().setContentType(ProtostuffConstant.PROTOBUF_MEDIA_TYPE);
		}
	}

}