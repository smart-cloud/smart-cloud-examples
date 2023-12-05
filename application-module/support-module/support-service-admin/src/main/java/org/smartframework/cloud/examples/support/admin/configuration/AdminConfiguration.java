package org.smartframework.cloud.examples.support.admin.configuration;

import org.smartframework.cloud.examples.support.admin.properties.MonitorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration {

    @Bean
    public MonitorProperties monitorProperties() {
        return new MonitorProperties();
    }

}