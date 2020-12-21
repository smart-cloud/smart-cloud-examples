package org.smartframework.cloud.examples.support.gateway.configure;

import org.smartframework.cloud.examples.support.gateway.http.codec.ProtostuffHttpMessageReader;
import org.smartframework.cloud.examples.support.gateway.http.codec.ProtostuffHttpMessageWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@ConditionalOnProperty(name = "smart.rpc.protostuff", havingValue = "true", matchIfMissing = true)
public class RpcProtostuffConfigurer implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.customCodecs().reader(new ProtostuffHttpMessageReader());
        configurer.customCodecs().writer(new ProtostuffHttpMessageWriter());
    }

}