package org.smartframework.cloud.examples.framework.annotation;

import org.smartframework.cloud.starter.core.support.annotation.SmartBootApplication;
import org.smartframework.cloud.starter.core.support.annotation.YamlScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableFeignClients(basePackages = "org.smartframework.cloud.examples.**..rpc")
@EnableDiscoveryClient
@EnableCircuitBreaker
@SmartBootApplication(componentBasePackages = "org.smartframework.cloud.examples")
@YamlScan(locationPatterns = "classpath*:/application-*.yml")
public @interface SmartApplication {

}