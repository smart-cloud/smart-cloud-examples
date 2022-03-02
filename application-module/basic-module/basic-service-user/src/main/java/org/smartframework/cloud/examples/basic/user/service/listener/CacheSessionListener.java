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
package org.smartframework.cloud.examples.basic.user.service.listener;

import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.event.CacheSessionEvent;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 缓存会话事件监听
 *
 * @author collin
 * @date 2022-03-02
 */
@Component
@RequiredArgsConstructor
public class CacheSessionListener implements ApplicationListener<CacheSessionEvent> {

    private final LoginInfoApiService loginInfoApiService;

    @Override
    public void onApplicationEvent(CacheSessionEvent event) {
        loginInfoApiService.cacheUserInfo(event.getToken(), LoginRespVO.builder()
                .userId(event.getUid())
                .username(event.getUsername())
                .mobile(event.getMobile())
                .realName(event.getRealName()).build());
    }

}