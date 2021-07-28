package org.smartframework.cloud.examples.support.gateway.filter.access.core.datasecurity;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.smartframework.cloud.api.core.annotation.enums.SignType;
import org.smartframework.cloud.common.web.constants.SmartHttpHeaders;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayConstants;
import org.smartframework.cloud.examples.support.gateway.enums.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.exception.AesKeyNotFoundException;
import org.smartframework.cloud.examples.support.gateway.exception.RequestSignFailException;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.smartframework.cloud.examples.support.gateway.util.RedisKeyHelper;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.smartframework.cloud.exception.DataValidateException;
import org.smartframework.cloud.exception.ParamValidateException;
import org.smartframework.cloud.starter.core.constants.SymbolConstant;
import org.smartframework.cloud.utility.security.AesUtil;
import org.smartframework.cloud.utility.security.RsaUtil;
import org.springframework.core.io.buffer.DataBuffer;
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
import java.util.Arrays;

/**
 * 请求参数签名校验、解密
 *
 * @author liyulin
 * @date 2020-07-21
 */
@Slf4j
public class DataSecurityServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private transient Flux<DataBuffer> body;
    private transient RedissonClient redissonClient;
    private transient SecurityKeyCache securityKeyCache;

    DataSecurityServerHttpRequestDecorator(ServerHttpRequest delegate, String token, boolean requestDecrypt, byte signType, RedissonClient redissonClient) {
        super(delegate);
        Flux<DataBuffer> flux = super.getBody();
        this.redissonClient = redissonClient;

        final String requestStr = getEncryptedRequestStr(delegate);

        // 请求信息验签
        checkRequestSign(delegate, signType, requestStr, token);

        if (requestDecrypt) {
            flux.subscribe(buffer -> {
                SecurityKeyCache securityKeyCache = getSecurityKeyCache(token);
                String aesKey = securityKeyCache.getAesKey();
                if (StringUtils.isBlank(aesKey)) {
                    throw new AesKeyNotFoundException();
                }
                String decryptedRequestStr = AesUtil.decrypt(requestStr, aesKey);

                HttpMethod httpMethod = delegate.getMethod();
                if (httpMethod == HttpMethod.GET) {
                    decryptUrlParams(decryptedRequestStr, delegate.getQueryParams());
                } else if (httpMethod == HttpMethod.POST) {
                    MediaType contentType = delegate.getHeaders().getContentType();
                    if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType.toString())) {
                        decryptUrlParams(decryptedRequestStr, delegate.getQueryParams());
                    } else if (MediaType.APPLICATION_JSON_VALUE.equals(contentType.toString())) {
                        this.body = Flux.just(RewriteHttpUtil.convert(decryptedRequestStr.getBytes(StandardCharsets.UTF_8)));
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
                    queryParams.put(entry[0], Lists.newArrayList(value));
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
            RSAPublicKey publicKey = RsaUtil.getRSAPublidKey(securityKeyCache.getCpubKeyModulus(), securityKeyCache.getCpubKeyExponent());
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
        RMapCache<String, SecurityKeyCache> authCache = redissonClient.getMapCache(RedisKeyHelper.getSecurityHashKey());
        securityKeyCache = authCache.get(RedisKeyHelper.getSecurityKey(token));
        if (securityKeyCache == null) {
            throw new DataValidateException(GatewayReturnCodes.SECURITY_KEY_EXPIRED);
        }
        return securityKeyCache;
    }

}