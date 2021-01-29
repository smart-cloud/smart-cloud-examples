package org.smartframework.cloud.examples.support.gateway.filter.access;

import org.smartframework.cloud.api.core.annotation.SmartApiAcess;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * {@link SmartApiAcess}对应入参处理，供后续filter使用
 *
 * @author liyulin
 * @date 2020-09-08
 */
@Configuration
public class ApiAccessFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String urlMethod = request.getURI().getPath() + request.getMethodValue();
        ApiMetaFetchRespVO.ApiAccess apiAC = (ApiMetaFetchRespVO.ApiAccess) redisTemplate.opsForHash().get(RedisKeyHelper.getApiMetaKey(), RedisKeyHelper.getApiMetaHashKey(urlMethod));

        String token = WebUtil.getFromRequestHeader(request, GatewayConstants.AccessConstants.TOKEN);

        // 将数据塞入当前context，供后面filter使用
        ApiAccessBO apiAccessBO = new ApiAccessBO().setApiAccess(apiAC).setToken(token);
        ApiAccessContext.setContext(apiAccessBO);
        return chain.filter(exchange).doFinally(s -> {
            // 使用完清理，避免内存泄漏
            ApiAccessContext.clear();
        });
    }

    @Override
    public int getOrder() {
        return Order.API_ACCESS;
    }

}