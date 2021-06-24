package org.smartframework.cloud.examples.support.gateway.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //允许哪种方法类型跨域 get post delete put
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        // 允许哪些请求源跨域
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
        // 是否携带cookie跨域
        corsConfiguration.setAllowCredentials(true);

        //允许跨域的路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}