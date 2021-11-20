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
package org.smartframework.cloud.examples.support.eureka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurekaStateChangeListener {

	@EventListener
	public void listen(EurekaInstanceCanceledEvent event) {
		// 服务下线事件
		log.info("服务:{}|{}挂了", event.getAppName(), event.getServerId());
		// TODO:下线通知
	}

	@EventListener
	public void listen(EurekaInstanceRegisteredEvent event) {
		// 服务注册事件
		log.info("服务:{}|{}注册成功了", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
		// TODO:注册通知
	}

	@EventListener
	public void listen(EurekaInstanceRenewedEvent event) {
		// 服务续约事件
		log.info("心跳检测:{}|{}", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
	}

	@EventListener
	public void listen(EurekaRegistryAvailableEvent event) {
		// 注册中心启动事件
	}

	@EventListener
	public void listen(EurekaServerStartedEvent event) {
		// Server启动
	}
	
}