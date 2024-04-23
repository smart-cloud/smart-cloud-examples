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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.smart.cloud.api.core.annotation.enums.SignType;
import io.github.smart.cloud.utility.JacksonUtil;
import io.github.smart.cloud.utility.NonceUtil;
import io.github.smart.cloud.utility.security.AesUtil;
import io.github.smart.cloud.utility.security.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.smartframework.cloud.examples.support.gateway.cache.SecurityKeyCache;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;
import org.smartframework.cloud.examples.support.gateway.constants.ResponseFields;
import org.smartframework.cloud.examples.support.gateway.exception.GenerateSignFailException;
import org.smartframework.cloud.examples.support.gateway.exception.GenerateSignKeyFailException;
import org.smartframework.cloud.examples.support.gateway.exception.UnsupportedFunctionException;
import org.smartframework.cloud.examples.support.gateway.util.RewriteHttpUtil;
import org.smartframework.cloud.examples.support.gateway.util.SignUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;

/**
 * 响应信息加密、签名
 *
 * @author collin
 * @date 2020-07-21
 */
@Slf4j
public class DataSecurityServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private final SecurityKeyCache securityKeyCache;
    /**
     * 返回信息是否需要签名
     */
    private final boolean isSign;
    /**
     * 是否支持加密、签名
     */
    private final boolean isSupported;
    /**
     * 返回体是否需要加密
     */
    private final boolean encrypt;
    private transient Publisher<? extends DataBuffer> body;

    DataSecurityServerHttpResponseDecorator(ServerHttpResponse response, SecurityKeyCache securityKeyCache, boolean responseEncrypt, byte signType) {
        super(response);

        this.isSupported = RewriteHttpUtil.isSupported(super.getHeaders().getContentType());
        this.isSign = signType == SignType.RESPONSE.getType() || signType == SignType.ALL.getType();
        this.encrypt = responseEncrypt;
        if ((isSign || encrypt) && !isSupported) {
            throw new UnsupportedFunctionException(GatewayReturnCodes.NOT_SUPPORT_DATA_SECURITY);
        }

        this.securityKeyCache = securityKeyCache;
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (!isSupported || (!isSign && !encrypt)) {
            return super.writeWith(body);
        }

        Flux.from(body).subscribe(buffer -> {
            byte[] bytes = RewriteHttpUtil.convert(buffer);
            String originBodyStr = new String(bytes, StandardCharsets.UTF_8);
            if (StringUtils.isBlank(originBodyStr)) {
                this.body = Flux.empty();
                return;
            }

            ObjectNode originResponseNode = JacksonUtil.parseObject(originBodyStr, ObjectNode.class);
            if (originResponseNode == null) {
                this.body = Flux.empty();
                return;
            }

            // 1、响应体塞入timestamp、nonce、sign
            JsonNode timestampNode = originResponseNode.get(ResponseFields.TIMESTAMP);
            long timestamp;
            if (timestampNode == null) {
                timestamp = System.currentTimeMillis();
                originResponseNode.put(ResponseFields.TIMESTAMP, timestamp);
            } else {
                timestamp = timestampNode.asLong();
            }

            JsonNode nonceNode = originResponseNode.get(ResponseFields.NONCE);
            String nonce = null;
            if (nonceNode == null) {
                nonce = String.valueOf(NonceUtil.nextId());
                originResponseNode.put(ResponseFields.NONCE, nonce);
            } else {
                nonce = nonceNode.asText();
            }

            // 2、Response#body加密处理
            JsonNode bodyNode = originResponseNode.get(ResponseFields.BODY);
            String encryptedBody = null;
            if (encrypt) {
                if (bodyNode != null) {
                    encryptedBody = AesUtil.encrypt(bodyNode.asText(), securityKeyCache.getAesKey());
                }
            }

            // 3、body加密后，base64处理
            String base64EncryptedBody = null;
            if (encryptedBody != null) {
                base64EncryptedBody = Base64Utils.encodeToString(encryptedBody.getBytes(StandardCharsets.UTF_8));
                originResponseNode.put(ResponseFields.BODY, base64EncryptedBody);
            }

            // 4、签名处理
            if (isSign) {
                String signContent = SignUtil.generateResponseSignContent(nonce, timestamp, base64EncryptedBody);
                RSAPrivateKey rsaPrivateKey = null;
                try {
                    rsaPrivateKey = RsaUtil.getRsaPrivateKey(securityKeyCache.getCpubKeyExponent(), securityKeyCache.getCpubKeyExponent());
                } catch (DecoderException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                    log.error("generate sign key fail", e);
                    throw new GenerateSignKeyFailException();
                }

                String sign = null;
                try {
                    sign = RsaUtil.sign(signContent, rsaPrivateKey);
                } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | SignatureException |
                         UnsupportedEncodingException e) {
                    log.error("generate sign fail", e);
                    throw new GenerateSignFailException();
                }
                originResponseNode.put(ResponseFields.SIGN, sign);
            }

            this.body = Flux.just(super.bufferFactory().wrap(originResponseNode.asText().getBytes(StandardCharsets.UTF_8)));
        });

        return super.writeWith(this.body);
    }

}