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
package org.smartframework.cloud.examples.support.gateway.configure;

import org.apache.commons.collections4.ListUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 网关跨域配置
 *
 * @author collin
 * @date 2021-06-24
 */
@Configuration
public class GatewayCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许哪种请求头跨域
        corsConfiguration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        //允许哪种方法类型跨域 get post delete put
        corsConfiguration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        // 允许哪些请求源跨域
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(CorsConfiguration.ALL));
        // 是否携带cookie跨域
        corsConfiguration.setAllowCredentials(true);

        //允许跨域的路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}