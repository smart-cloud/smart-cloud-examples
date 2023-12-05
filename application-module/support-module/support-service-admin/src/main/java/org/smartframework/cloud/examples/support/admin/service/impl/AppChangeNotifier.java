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

    private final IRobotService robotService;
    /**
     * 风控服务名
     */
    private static final Set<String> RISK_SERVICE_NAMES = new HashSet<>(8);
    /**
     * 监控服务名
     */
    private static final String MONITOR_SERVICE_NAME = "monitor";

    static {
        RISK_SERVICE_NAMES.add("risk-rule");
        RISK_SERVICE_NAMES.add("risk-approval");
        RISK_SERVICE_NAMES.add("risk-collection");
        RISK_SERVICE_NAMES.add("auth-provider");
        RISK_SERVICE_NAMES.add("field-compute");
    }

    public AppChangeNotifier(IRobotService robotService, InstanceRepository repository) {
        super(repository);
        this.robotService = robotService;
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        return event instanceof InstanceStatusChangedEvent;
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            Registration registration = instance.getRegistration();
            // 排除掉监控服务本身
            if (MONITOR_SERVICE_NAME.equals(registration.getName())) {
                return;
            }

            StatusInfo statusInfo = instance.getStatusInfo();

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

            RobotTemplateCode robotTemplateCode = RISK_SERVICE_NAMES.contains(registration.getName()) ? RobotTemplateCode.RISK : RobotTemplateCode.BUSINESS;
            robotService.sendWxworkNotice(robotTemplateCode, registration.getName(), registration.getServiceUrl(), state);
        });
    }

}