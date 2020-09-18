package org.smartframework.cloud.examples.support.admin.service;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author liyulin
 * @date 2020-09-15
 */
@Component
@Slf4j
public class AppChangeNotifier extends AbstractStatusChangeNotifier {

    public AppChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            Registration registration = instance.getRegistration();
            StatusInfo statusInfo = instance.getStatusInfo();
            if (statusInfo.isDown() || statusInfo.isOffline()) {
                // send email
            }
            log.info("{}==>{}", registration.getName(), statusInfo.getStatus());
        });
    }

}