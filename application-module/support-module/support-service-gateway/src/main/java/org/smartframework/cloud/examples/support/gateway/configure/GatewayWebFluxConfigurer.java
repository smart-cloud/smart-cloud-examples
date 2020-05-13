package org.smartframework.cloud.examples.support.gateway.configure;

import org.smartframework.cloud.examples.support.gateway.http.codec.ProtostuffHttpMessageReader;
import org.smartframework.cloud.examples.support.gateway.http.codec.ProtostuffHttpMessageWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class GatewayWebFluxConfigurer implements WebFluxConfigurer {
	
	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		configurer.customCodecs().reader(new ProtostuffHttpMessageReader());
		configurer.customCodecs().writer(new ProtostuffHttpMessageWriter());
	}
	
}