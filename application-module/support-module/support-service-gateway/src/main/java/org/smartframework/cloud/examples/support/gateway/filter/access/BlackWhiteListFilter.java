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
package org.smartframework.cloud.examples.support.gateway.filter.access;

import lombok.RequiredArgsConstructor;
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

import java.util.Set;

/**
 * 黑、白名单校验
 *
 * @author collin
 * @date 2024-03-26
 */
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
        if (!blackWhiteListProperties.isEnable()) {
            return chain.filter(exchange);
        }

        String url = exchange.getRequest().getURI().getPath();
        String ipAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        checkBlackList(url, ipAddress, blackWhiteListProperties.getBlackIpList());
        checkWhiteList(url, ipAddress, blackWhiteListProperties.getWhiteIpList());

        return chain.filter(exchange);
    }

    /**
     * 检查黑名单
     *
     * @param url
     * @param ipAddress
     * @param blackIpList
     */
    private void checkBlackList(String url, String ipAddress, BlackWhiteListProperties.IpList blackIpList) {
        Set<String> globalBlackIps = blackIpList.getIpList();
        Set<String> urlBlackIps = blackIpList.getUrlIpList().get(url);
        if (CollectionUtils.isEmpty(globalBlackIps) && CollectionUtils.isEmpty(urlBlackIps)) {
            return;
        }

        for (String globalBlackIp : globalBlackIps) {
            if (ipAddress.startsWith(globalBlackIp)) {
                throw new BlackListException();
            }
        }

        for (String urlBlackIp : urlBlackIps) {
            if (ipAddress.startsWith(urlBlackIp)) {
                throw new BlackListException();
            }
        }
    }

    /**
     * 检查白名单
     *
     * @param url
     * @param ipAddress
     * @param whiteIpList
     */
    private void checkWhiteList(String url, String ipAddress, BlackWhiteListProperties.IpList whiteIpList) {
        Set<String> globalIpList = whiteIpList.getIpList();
        if (CollectionUtils.isNotEmpty(globalIpList)) {
            for (String globalIp : globalIpList) {
                if (ipAddress.startsWith(globalIp)) {
                    return;
                }
            }
        }

        Set<String> urlWhiteIps = whiteIpList.getUrlIpList().get(url);
        if (CollectionUtils.isNotEmpty(urlWhiteIps)) {
            boolean meetUrlWhiteList = false;
            for (String urlWhiteIp : urlWhiteIps) {
                if (ipAddress.startsWith(urlWhiteIp)) {
                    meetUrlWhiteList = true;
                    break;
                }
            }
            if (!meetUrlWhiteList) {
                throw new WhiteListException();
            }
        }
    }

}