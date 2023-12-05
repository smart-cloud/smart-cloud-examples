package org.smartframework.cloud.examples.support.admin.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.admin.enums.RobotTemplateCode;
import org.smartframework.cloud.examples.support.admin.properties.MonitorProperties;
import org.smartframework.cloud.examples.support.admin.properties.ProxyProperties;
import org.smartframework.cloud.examples.support.admin.properties.RobotProperties;
import org.smartframework.cloud.examples.support.admin.service.IRobotService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements IRobotService {

    private final MonitorProperties monitorProperties;

    @Override
    public void sendWxworkNotice(RobotTemplateCode robotTemplateCode, String name, String address, String state) {
        RobotProperties robotProperties = monitorProperties.getRobots().get(robotTemplateCode.getCode());
        sendWxworkNotice(robotProperties.getUrl(), String.format(robotProperties.getMessageTemplate(), name, address, state));
    }

    private void sendWxworkNotice(String url, String content) {
        try {
            HttpRequest httpRequest = HttpUtil.createPost(url).body(content.getBytes(StandardCharsets.UTF_8)).setConnectionTimeout(3000).setReadTimeout(3000);

            ProxyProperties proxyProperties = monitorProperties.getProxy();
            if (proxyProperties != null && (proxyProperties.getHost() != null && proxyProperties.getHost().trim().length() > 0) && proxyProperties.getPort() != null) {
                httpRequest.setHttpProxy(proxyProperties.getHost(), proxyProperties.getPort());
            }

            httpRequest.execute(true);
        } catch (Exception e) {
            log.error("send restart flink job notice fail|url={}, content={}", url, content, e);
        }
    }

}