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
package org.smartframework.cloud.examples.support.gateway.util;

import io.github.smart.cloud.common.web.constants.SmartHttpHeaders;
import io.github.smart.cloud.utility.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.examples.support.gateway.constants.DataSecurityConstants;
import org.smartframework.cloud.examples.support.gateway.dto.DataSecurityParamDTO;
import org.smartframework.cloud.examples.support.gateway.exception.RequestNonceException;
import org.smartframework.cloud.examples.support.gateway.filter.rewrite.RewriteServerHttpRequestDecorator;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Map;
import java.util.TreeMap;

import static org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes.REQUEST_NONCE_MISSING;

/**
 * 签名工具类
 *
 * @author collin
 * @date 2024-04-22
 */
public class SignUtil {

    /**
     * 1.参数定义
     * H=http headers（按自然排序的json串）
     * Q=BASE64(url参数)
     * B=BASE64(body的json串)
     * 2.sign = RSA签名（“httpmethod + H + Q + B”）
     *
     * @param httpMethod
     * @param dataSecurityParam
     * @return
     */
    public static String generateRequestSignContent(HttpMethod httpMethod, DataSecurityParamDTO dataSecurityParam) {
        StringBuilder content = new StringBuilder(64);
        content.append(httpMethod);
        content.append(dataSecurityParam.getHeaders());
        if (StringUtils.isNotBlank(dataSecurityParam.getUrlParamsBase64())) {
            content.append(dataSecurityParam.getUrlParamsBase64());
        }
        if (StringUtils.isNotBlank(dataSecurityParam.getBodyBase64())) {
            content.append(dataSecurityParam.getBodyBase64());
        }
        return content.toString();
    }

    /**
     * 获取安全（解解密、签名）参数
     *
     * @param request
     * @return
     */
    public static DataSecurityParamDTO getDataSecurityParams(ServerHttpRequest request) {
        String headerSignContent = buildHeaderContent(request);
        String urlSignContent = getUrlContent(request);
        String bodyContent = getRequestBodyContent(request);

        DataSecurityParamDTO dataSecurityParam = new DataSecurityParamDTO();
        dataSecurityParam.setHeaders(headerSignContent);
        dataSecurityParam.setUrlParamsBase64(urlSignContent);
        dataSecurityParam.setBodyBase64(bodyContent);

        return dataSecurityParam;
    }


    public static String getRequestBodyContent(ServerHttpRequest request) {
        if (!(request instanceof RewriteServerHttpRequestDecorator)) {
            return null;
        }

        RewriteServerHttpRequestDecorator rewriteServerHttpRequestDecorator = (RewriteServerHttpRequestDecorator) request;
        return rewriteServerHttpRequestDecorator.getBodyStr();
    }

    public static String getUrlContent(ServerHttpRequest request) {
        return request.getQueryParams().getFirst(DataSecurityConstants.URL_PARAM_NAME);
    }

    public static String buildHeaderContent(ServerHttpRequest request) {
        String nonce = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.NONCE);
        if (StringUtils.isBlank(nonce)) {
            throw new RequestNonceException(REQUEST_NONCE_MISSING);
        }

        String timestamp = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.TIMESTAMP);
        String token = WebUtil.getFromRequestHeader(request, SmartHttpHeaders.TOKEN);

        Map<String, String> headerSignData = new TreeMap<>();
        headerSignData.put(SmartHttpHeaders.TIMESTAMP, timestamp);
        headerSignData.put(SmartHttpHeaders.NONCE, nonce);
        headerSignData.put(SmartHttpHeaders.TOKEN, token);

        return JacksonUtil.toJson(headerSignData);
    }

}