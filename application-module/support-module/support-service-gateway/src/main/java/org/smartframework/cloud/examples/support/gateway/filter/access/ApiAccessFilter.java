package org.smartframework.cloud.examples.support.gateway.filter.access;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.web.constants.SmartHttpHeaders;
import org.smartframework.cloud.examples.support.gateway.cache.ApiAccessMetaCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.filter.FilterContext;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * smart cloud自定义注解对应入参处理，供后续filter使用
 *
 * @author liyulin
 * @date 2020-09-08
 */
@Component
@Slf4j
public class ApiAccessFilter implements WebFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String urlMethod = request.getURI().getPath() + request.getMethodValue();
        ApiAccessMetaCache apiAccessMetaCache = (ApiAccessMetaCache) redisTemplate.opsForHash().get(RedisKeyHelper.getApiMetaKey(),
                RedisKeyHelper.getApiMetaHashKey(urlMethod));

        String token = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.TOKEN);

        // 将数据塞入当前context，供后面filter使用
        FilterContext filterContext = new FilterContext()
                .setToken(token)
                .setApiAccessMetaCache(apiAccessMetaCache)
                .setUrlMethod(urlMethod);
        exchange.getAttributes().put(GatewayConstants.FILTER_CONTEXT_KEY, filterContext);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Order.API_ACCESS;
    }

}