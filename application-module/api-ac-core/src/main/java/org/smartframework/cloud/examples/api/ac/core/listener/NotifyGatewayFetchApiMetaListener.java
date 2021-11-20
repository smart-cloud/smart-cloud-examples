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
package org.smartframework.cloud.examples.api.ac.core.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.api.ac.core.properties.ApiAccessProperties;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.NotifyFetchReqDTO;
import org.smartframework.cloud.exception.ServerException;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 上传接口信息给网关
 * <p>为了接口安全，api meta信息不由服务主动传给gateway，而是服务去通知gateway，由gateway主动获取</p>
 *
 * @author collin
 * @date 2020/04/14
 */
@Slf4j
@AllArgsConstructor
public class NotifyGatewayFetchApiMetaListener implements ApplicationListener<ApplicationReadyEvent> {

    private ApiMetaRpc apiMetaRpc;
    private ApiAccessProperties apiAccessProperties;
    /**
     * 服务名key
     */
    private static final String SERVICE_NAME_KEY = "spring.application.name";

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!apiAccessProperties.isUploadApiMeta()) {
            log.warn("notify gateway to fetch api meta is ignored!");
            return;
        }

        log.info("notify gateway to fetch api meta to gateway start!");
        String serviceName = event.getApplicationContext().getEnvironment().getProperty(SERVICE_NAME_KEY);
        Response<Base> result = apiMetaRpc.notifyFetch(NotifyFetchReqDTO.builder().serviceName(serviceName).build());
        log.info("notify gateway to fetch api meta to gateway finish!");

        if (!RespUtil.isSuccess(result)) {
            throw new ServerException(RespUtil.getFailMsg(result));
        }
    }

}