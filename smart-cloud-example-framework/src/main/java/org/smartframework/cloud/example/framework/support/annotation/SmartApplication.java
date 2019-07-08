package org.smartframework.cloud.example.framework.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.smartframework.cloud.starter.common.support.annotation.SmartSpringCloudApplication;
import org.smartframework.cloud.starter.common.support.annotation.YamlScan;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SmartSpringCloudApplication(componentBasePackages = "org.smartframework.cloud.example", feignClientBasePackages = "org.smartframework.cloud.example.rpc")
@YamlScan(locationPatterns = "classpath*:/application-*.yml")
public @interface SmartApplication {

}