package org.smartframework.cloud.examples.support.admin.service;

import org.smartframework.cloud.examples.support.admin.enums.RobotTemplateCode;

public interface IRobotService {

    void sendWxworkNotice(RobotTemplateCode robotTemplateCode, String name, String address, String state, String healthInstanceCountDesc);

}