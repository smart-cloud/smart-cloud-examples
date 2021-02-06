package org.smartframework.cloud.examples.basic.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所在平台=={"1":"app","2":"web后台","3":"微信"}
 *
 * @author liyulin
 * @date 2019-06-29
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChannelEnum {

    /**
     * app
     */
    APP((byte) 1),
    /**
     * web后台
     */
    WEB((byte) 2),
    /**
     * 微信
     */
    WEIXIN((byte) 3);

    private byte value;

}