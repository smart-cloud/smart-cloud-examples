package org.smartframework.cloud.examples.support.gateway.configure;

import org.smartframework.cloud.examples.support.gateway.properties.BlackWhiteListProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 *
 * @author collin
 * @date 2024-03-27
 */
@Configuration
public class SmartGatewayConfiguration {

    @Bean
    @RefreshScope
    public BlackWhiteListProperties blackWhiteListProperties() {
        return new BlackWhiteListProperties();
    }

}