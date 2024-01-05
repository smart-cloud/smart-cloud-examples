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
package org.smartframework.cloud.examples.support.admin.service.impl;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.admin.enums.RobotTemplateCode;
import org.smartframework.cloud.examples.support.admin.service.IRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务状态变更监听
 *
 * @author collin
 * @date 2020-09-15
 */
@Slf4j
@Component
public class AppChangeNotifier extends AbstractStatusChangeNotifier {

    @Autowired
    private IRobotService robotService;
    private final InstanceRepository instanceRepository;
    /**
     * 风控服务名
     */
    private static final Set<String> MONITOR_SERVICE_NAME = new HashSet<>(8);
    /**
     * 服务启动时间
     */
    private static final long START_UP_TS = System.currentTimeMillis();

    static {
        MONITOR_SERVICE_NAME.add("risk-rule");
        MONITOR_SERVICE_NAME.add("risk-approval");
        MONITOR_SERVICE_NAME.add("risk-collection");
        MONITOR_SERVICE_NAME.add("auth-provider");
        MONITOR_SERVICE_NAME.add("field-compute");
    }

    public AppChangeNotifier(InstanceRepository repository) {
        super(repository);
        this.instanceRepository = repository;
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        return event instanceof InstanceStatusChangedEvent;
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            Registration registration = instance.getRegistration();
            if (System.currentTimeMillis() - START_UP_TS <= 60000) {
                //服务启动时，1分钟内的服务通知过滤掉
                return;
            }

            // 排除掉监控服务本身
            if (MONITOR_SERVICE_NAME.equals(registration.getName())) {
                return;
            }

            StatusInfo statusInfo = instance.getStatusInfo();

            // 服务状态描述
            String state;
            if (statusInfo.isDown()) {
                state = "<font color=\\\"comment\\\">**健康检查没通过**</font>";
            } else if (statusInfo.isUp()) {
                state = "<font color=\\\"info\\\">**上线**</font>";
            } else if (statusInfo.isOffline()) {
                state = "<font color=\\\"warning\\\">**离线**</font>";
            } else if (statusInfo.isUnknown()) {
                state = "<font color=\\\"comment\\\">**未知异常**</font>";
            } else {
                state = "**unknow**";
            }
            log.info("{}==>{}", registration.getName(), statusInfo.getStatus());

            Long healthInstanceCount = instanceRepository.findByName(registration.getName())
                    .filter(item -> item.getStatusInfo().isUp())
                    .count()
                    .share()
                    .block();
            // 在线实例数
            String healthInstanceCountDesc = healthInstanceCount > 0 ? String.valueOf(healthInstanceCount) : "<font color=\\\"warning\\\">**0**</font>";

            RobotTemplateCode robotTemplateCode = MONITOR_SERVICE_NAME.contains(registration.getName()) ? RobotTemplateCode.RISK : RobotTemplateCode.BUSINESS;
            robotService.sendWxworkNotice(robotTemplateCode, registration.getName(), registration.getServiceUrl(), state, healthInstanceCountDesc);
        });
    }

}