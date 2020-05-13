//package org.smartframework.cloud.examples.support.gateway.configure;
//
//import org.smartframework.cloud.examples.support.gateway.constants.ProtostuffConstants;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RoutesConfigurer {
//
//	@Bean
//	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
//		return routeLocatorBuilder.routes().route(r -> r.path("**/rpc/**")
//				.filters(f -> f.setResponseHeader("content-type", ProtostuffConstants.PROTOBUF_MEDIA_TYPE.getType()))
//				.uri("")).build();
//	}
//
//}