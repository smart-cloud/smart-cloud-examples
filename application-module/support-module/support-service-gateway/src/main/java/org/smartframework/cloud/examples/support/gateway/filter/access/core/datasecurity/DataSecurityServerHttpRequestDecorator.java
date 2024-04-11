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
package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import io.github.smart.cloud.api.core.annotation.enums.SignType;
import io.github.smart.cloud.common.web.constants.SmartHttpHeaders;
import io.github.smart.cloud.constants.SymbolConstant;
import io.github.smart.cloud.exception.DataValidateException;
import io.github.smart.cloud.exception.ParamValidateException;
import io.github.smart.cloud.starter.redis.adapter.IRedisAdapter;
import io.github.smart.cloud.utility.security.AesUtil;
import io.github.smart.cloud.utility.security.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.AesKeyNotFoundException;
import org.smartframework.cloud.examples.support.gateway.exception.RequestSignFailException;
import org.smartframework.cloud.examples.support.gateway.exception.UnsupportedFunctionException;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 请求参数签名校验、解密
 *
 * @author collin
 * @date 2020-07-21
 */
@Slf4j
public class DataSecurityServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private transient Flux<DataBuffer> body;
    private final IRedisAdapter redisAdapter;
    private transient SecurityKeyCache securityKeyCache;

    DataSecurityServerHttpRequestDecorator(ServerHttpRequest request, DataBufferFactory dataBufferFactory, String token, boolean requestDecrypt, byte signType, IRedisAdapter redisAdapter) {
        super(request);

        if ((requestDecrypt || signType == SignType.REQUEST.getType() || signType == SignType.ALL.getType())
                && !RewriteHttpUtil.isSupported(super.getHeaders().getContentType())) {
            throw new UnsupportedFunctionException(GatewayReturnCodes.NOT_SUPPORT_DATA_SECURITY);
        }

        Flux<DataBuffer> flux = super.getBody();
        this.redisAdapter = redisAdapter;

        final String requestStr = getEncryptedRequestStr(request);

        // 请求信息验签
        checkRequestSign(request, signType, requestStr, token);

        if (requestDecrypt) {
            flux.subscribe(buffer -> {
                SecurityKeyCache securityKeyCache = getSecurityKeyCache(token);
                String aesKey = securityKeyCache.getAesKey();
                if (StringUtils.isBlank(aesKey)) {
                    throw new AesKeyNotFoundException();
                }
                String decryptedRequestStr = AesUtil.decrypt(requestStr, aesKey);

                HttpMethod httpMethod = request.getMethod();
                if (httpMethod == HttpMethod.GET) {
                    decryptUrlParams(decryptedRequestStr, request.getQueryParams());
                } else if (httpMethod == HttpMethod.POST) {
                    MediaType contentType = request.getHeaders().getContentType();
                    if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())) {
                        decryptUrlParams(decryptedRequestStr, request.getQueryParams());
                    } else if (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())) {
                        this.body = Flux.just(dataBufferFactory.wrap(decryptedRequestStr.getBytes(StandardCharsets.UTF_8)));
                    }
                }
            });
        } else {
            this.body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

    private void decryptUrlParams(String decryptedRequestStr, MultiValueMap<String, String> queryParams) {
        queryParams.remove(GatewayConstants.REQUEST_ENCRYPT_PARAM_NAME);
        Arrays.stream(decryptedRequestStr.split(SymbolConstant.AND)).forEach(param -> {
            if (param.contains(SymbolConstant.EQUAL)) {
                String[] entry = param.split(SymbolConstant.EQUAL);
                if (entry.length > 0) {
                    String value = entry[1];
                    if (StringUtils.isNotBlank(value)) {
                        try {
                            value = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
                        } catch (UnsupportedEncodingException e) {
                            log.error("decode.error|value={}", value, e);
                        }
                    }
                    List<String> values = new ArrayList<>(1);
                    values.add(value);
                    queryParams.put(entry[0], values);
                }
            }
        });
    }

    /**
     * 获取加密后的请求参数
     *
     * @param request
     * @return
     */
    private String getEncryptedRequestStr(ServerHttpRequest request) {
        String requestStr = null;
        HttpMethod httpMethod = request.getMethod();
        if (httpMethod == HttpMethod.GET) {
            requestStr = request.getQueryParams().getFirst(GatewayConstants.REQUEST_ENCRYPT_PARAM_NAME);
        } else if (httpMethod == HttpMethod.POST) {
            MediaType contentType = request.getHeaders().getContentType();
            if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())) {
                requestStr = request.getQueryParams().getFirst(GatewayConstants.REQUEST_ENCRYPT_PARAM_NAME);
            } else if (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())) {
                RewriteServerHttpRequestDecorator rewriteServerHttpRequestDecorator = (RewriteServerHttpRequestDecorator) request;
                requestStr = rewriteServerHttpRequestDecorator.getBodyStr();
            }
        }
        return requestStr;
    }

    /**
     * 请求参数签名校验
     *
     * @param request
     * @param signType
     * @param token
     */
    private void checkRequestSign(ServerHttpRequest request, byte signType, String requestStr, @NonNull String token) {
        if (StringUtils.isBlank(requestStr)) {
            return;
        }
        if (SignType.REQUEST.getType() != signType && SignType.ALL.getType() != signType) {
            return;
        }
        String sign = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.SIGN);
        if (StringUtils.isBlank(sign)) {
            throw new ParamValidateException(GatewayReturnCodes.REQUEST_SIGN_MISSING);
        }

        SecurityKeyCache securityKeyCache = getSecurityKeyCache(token);
        boolean signCheckResult = false;
        try {
            RSAPublicKey publicKey = RsaUtil.getRsaPublidKey(securityKeyCache.getCpubKeyModulus(), securityKeyCache.getCpubKeyExponent());
            signCheckResult = RsaUtil.checkSign(requestStr, sign, publicKey);
        } catch (Exception e) {
            log.error("sign.check.error", e);
        }
        if (!signCheckResult) {
            throw new RequestSignFailException();
        }
    }

    private SecurityKeyCache getSecurityKeyCache(@NonNull String token) {
        if (securityKeyCache != null) {
            return securityKeyCache;
        }

        SecurityKeyCache securityKeyCache = (SecurityKeyCache) redisAdapter.get(RedisKeyHelper.getSecurityKey(token));
        if (securityKeyCache == null) {
            throw new DataValidateException(GatewayReturnCodes.SECURITY_KEY_EXPIRED);
        }
        return securityKeyCache;
    }

}