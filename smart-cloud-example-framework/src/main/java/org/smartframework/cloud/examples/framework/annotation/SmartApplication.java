/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.framework.annotation;

import io.github.smart.cloud.starter.core.support.annotation.SmartBootApplication;
import io.github.smart.cloud.starter.core.support.annotation.YamlScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableFeignClients(basePackages = "org.smartframework.cloud.examples.**..rpc")
@EnableDiscoveryClient
@SmartBootApplication(componentBasePackages = "org.smartframework.cloud.examples")
@YamlScan(locationPatterns = "classpath*:/application-*.yaml")
public @interface SmartApplication {

}