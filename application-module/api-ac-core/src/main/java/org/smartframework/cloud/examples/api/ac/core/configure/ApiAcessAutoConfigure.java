package org.smartframework.cloud.examples.api.ac.core.configure;

import org.smartframework.cloud.examples.api.ac.core.controller.ApiMetaController;
import org.smartframework.cloud.examples.api.ac.core.listener.NotifyGatewayFetchApiMetaListener;
import org.smartframework.cloud.examples.api.ac.core.properties.ApiAccessProperties;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(ApiAccessProperties.class)
public class ApiAcessAutoConfigure {

    @Autowired
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public NotifyGatewayFetchApiMetaListener notifyGatewayFetchApiMetaListener(ApiMetaRpc apiMetaRpc, ApiAccessProperties apiAccessProperties) {
        return new NotifyGatewayFetchApiMetaListener(apiMetaRpc, apiAccessProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiMetaController apiMetaController() {
        return new ApiMetaController();
    }

}