package org.smartframework.cloud.examples.support.admin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通知配置
 *
 * @author collin
 * @date 2023-04-24
 */
@Getter
@Setter
@ToString
public class RobotProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息模板
     */
    private String messageTemplate;

    /**
     * 企业微信机器人地址key
     */
    private String key;

    /**
     * 通知（企业微信机器人）地址
     */
    public String getUrl() {
        return String.format("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=%s", key);
    }

}