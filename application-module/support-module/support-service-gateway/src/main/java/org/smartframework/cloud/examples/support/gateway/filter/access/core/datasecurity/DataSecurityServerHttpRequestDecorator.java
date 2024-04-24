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
import io.github.smart.cloud.exception.ParamValidateException;
import io.github.smart.cloud.utility.security.AesUtil;
import io.github.smart.cloud.utility.security.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.dto.DataSecurityParamDTO;
import org.smartframework.cloud.examples.support.gateway.exception.AesKeyNotFoundException;
import org.smartframework.cloud.examples.support.gateway.exception.RequestSignCheckFailException;
import org.smartframework.cloud.examples.support.gateway.exception.UnsupportedFunctionException;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.smartframework.cloud.examples.support.gateway.util.SignUtil;
import org.smartframework.cloud.examples.support.gateway.util.WebUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;

/**
 * 请求参数签名校验、解密
 *
 * @author collin
 * @date 2020-07-21
 */
@Slf4j
public class DataSecurityServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private final SecurityKeyCache securityKeyCache;
    private transient Flux<DataBuffer> body;
    private transient URI uri;
    private transient MultiValueMap<String, String> queryParams;

    DataSecurityServerHttpRequestDecorator(ServerHttpRequest request, DataBufferFactory dataBufferFactory, SecurityKeyCache securityKeyCache, boolean requestDecrypt,
                                           byte signType) {
        super(request);
        this.securityKeyCache = securityKeyCache;

        if ((requestDecrypt || signType == SignType.REQUEST.getType() || signType == SignType.ALL.getType())) {
            if (!RewriteHttpUtil.isSupported(super.getHeaders().getContentType())) {
                throw new UnsupportedFunctionException(GatewayReturnCodes.NOT_SUPPORT_DATA_SECURITY);
            }

            checkSignAndDecryptRequest(request, dataBufferFactory, requestDecrypt, signType);
        } else {
            this.body = super.getBody();
            this.uri = super.getURI();
            this.queryParams = super.getQueryParams();
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return this.body;
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public MultiValueMap<String, String> getQueryParams() {
        return this.queryParams;
    }

    /**
     * 请求参数验签、解密
     *
     * @param request
     * @param dataBufferFactory
     * @param requestDecrypt
     * @param signType
     */
    private void checkSignAndDecryptRequest(ServerHttpRequest request, DataBufferFactory dataBufferFactory, boolean requestDecrypt, byte signType) {
        DataSecurityParamDTO dataSecurityParam = SignUtil.getDataSecurityParams(request);

        // 1、请求参数验签
        checkRequestSign(request, signType, dataSecurityParam);

        // 2、param base64 decode
        String base64DecodeUrlParams = StringUtils.isBlank(dataSecurityParam.getUrlParamsBase64()) ? null : new String(Base64Utils.decodeFromString(dataSecurityParam.getUrlParamsBase64()));
        String base64DecodeBody = StringUtils.isBlank(dataSecurityParam.getBodyBase64()) ? null : new String(Base64Utils.decodeFromString(dataSecurityParam.getBodyBase64()));

        if (base64DecodeUrlParams == null && base64DecodeBody == null) {
            setRealUriData(base64DecodeUrlParams, requestDecrypt, null);
            setRealBody(dataBufferFactory, base64DecodeBody, requestDecrypt, null);
            return;
        }

        // 3、解密
        if (!requestDecrypt) {
            // 重写base64解密后的参数
            setRealUriData(base64DecodeUrlParams, requestDecrypt, null);
            setRealBody(dataBufferFactory, base64DecodeBody, requestDecrypt, null);
            return;
        }

        String aesKey = securityKeyCache.getAesKey();
        if (StringUtils.isBlank(aesKey)) {
            throw new AesKeyNotFoundException();
        }

        setRealUriData(base64DecodeUrlParams, true, aesKey);
        setRealBody(dataBufferFactory, base64DecodeBody, true, aesKey);
    }

    /**
     * 重写body参数
     *
     * @param dataBufferFactory
     * @param base64DecodeBody
     * @param requestDecrypt
     * @param aesKey
     */
    private void setRealBody(DataBufferFactory dataBufferFactory, String base64DecodeBody, boolean requestDecrypt, String aesKey) {
        if (base64DecodeBody == null) {
            this.body = super.getBody();
            return;
        }

        String realBody = requestDecrypt ? AesUtil.decrypt(base64DecodeBody, aesKey) : base64DecodeBody;
        this.body = Flux.just(dataBufferFactory.wrap(realBody.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 重写url参数
     *
     * @param base64DecodeUrlParams
     * @param requestDecrypt
     * @param aesKey
     */
    private void setRealUriData(String base64DecodeUrlParams, boolean requestDecrypt, String aesKey) {
        if (base64DecodeUrlParams == null) {
            this.uri = super.getURI();
            this.queryParams = super.getQueryParams();
            return;
        }

        String realUrlParamsStr = requestDecrypt ? AesUtil.decrypt(base64DecodeUrlParams, aesKey) : base64DecodeUrlParams;
        UriComponents uriComponents = UriComponentsBuilder.fromUri(super.getURI()).replaceQuery(realUrlParamsStr).build();

        this.uri = uriComponents.toUri();
        this.queryParams = uriComponents.getQueryParams();
    }

    /**
     * 请求参数签名校验
     *
     * @param request
     * @param signType
     * @param dataSecurityParam
     */
    private void checkRequestSign(ServerHttpRequest request, byte signType, DataSecurityParamDTO dataSecurityParam) {
        if (SignType.REQUEST.getType() != signType && SignType.ALL.getType() != signType) {
            return;
        }

        String sign = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.SIGN);
        if (StringUtils.isBlank(sign)) {
            throw new ParamValidateException(GatewayReturnCodes.REQUEST_SIGN_MISSING);
        }

        String requestSignContent = SignUtil.generateRequestSignContent(request.getMethod(), dataSecurityParam);

        boolean signCheckResult = false;
        try {
            RSAPublicKey publicKey = RsaUtil.getRsaPublidKey(securityKeyCache.getCpubKeyModulus(), securityKeyCache.getCpubKeyExponent());
            signCheckResult = RsaUtil.checkSign(requestSignContent, sign, publicKey);
        } catch (Exception e) {
            log.error("sign.check.error", e);
        }
        if (!signCheckResult) {
            throw new RequestSignCheckFailException();
        }
    }

}