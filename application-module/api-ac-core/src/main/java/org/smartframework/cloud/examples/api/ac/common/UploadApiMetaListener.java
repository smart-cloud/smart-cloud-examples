package org.smartframework.cloud.examples.api.ac.common;

import org.smartframework.cloud.starter.core.constants.PackageConfig;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadApiMetaListener implements ApplicationListener<ApplicationStartedEvent> {
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		upload();
	}
	
	public void upload() {
		PackageConfig.getBasePackages();
	}
	
}