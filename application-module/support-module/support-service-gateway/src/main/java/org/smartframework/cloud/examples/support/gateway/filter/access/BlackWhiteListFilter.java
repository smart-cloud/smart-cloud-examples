package org.smartframework.cloud.examples.support.gateway.filter.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.examples.support.gateway.constants.Order;
import org.smartframework.cloud.examples.support.gateway.exception.BlackListException;
import org.smartframework.cloud.examples.support.gateway.exception.WhiteListException;
import org.smartframework.cloud.examples.support.gateway.properties.BlackWhiteListProperties;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

/**
 * 黑、白名单校验
 *
 * @author collin
 * @date 2024-03-26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BlackWhiteListFilter implements WebFilter, Ordered {

    private final BlackWhiteListProperties blackWhiteListProperties;

    @Override
    public int getOrder() {
        return Order.BLACK_WHITE_LIST;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        String ipAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        checkBlackList(url, ipAddress, blackWhiteListProperties.getBlackList());
        checkWhiteList(url, ipAddress, blackWhiteListProperties.getWhiteList());

        return chain.filter(exchange);
    }

    /**
     * 检查黑名单
     *
     * @param url
     * @param ipAddress
     * @param blackList
     */
    private void checkBlackList(String url, String ipAddress, Map<String, Set<String>> blackList) {
        Set<String> blackIps = blackList.get(url);
        if (CollectionUtils.isEmpty(blackIps)) {
            return;
        }

        for (String blackIp : blackIps) {
            if (ipAddress.startsWith(blackIp)) {
                throw new BlackListException();
            }
        }
    }

    /**
     * 检查白名单
     *
     * @param url
     * @param ipAddress
     * @param whiteList
     */
    private void checkWhiteList(String url, String ipAddress, Map<String, Set<String>> whiteList) {
        Set<String> whiteIps = whiteList.get(url);
        if (CollectionUtils.isEmpty(whiteIps)) {
            return;
        }

        boolean meetWhiteList = false;
        for (String whiteIp : whiteIps) {
            if (ipAddress.startsWith(whiteIp)) {
                meetWhiteList = true;
                break;
            }
        }

        if (!meetWhiteList) {
            throw new WhiteListException();
        }
    }

}