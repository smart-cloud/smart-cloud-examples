package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * gateway常量
 *
 * @author liyulin
 * @date 2020-08-07
 */
public interface GatewayConstants {

    /**
     * gateway接口url前缀
     */
    String GATEWAY_API_URL_PREFIX = "/gateway";

    interface AccessConstants {
        /**
         * http header timestamp
         */
        String TIMESTAMP = "smart-timestamp";
        /**
         * http header nonce
         */
        String NONCE = "smart-nonce";
        /**
         * http header token
         */
        String TOKEN = "smart-token";
    }

}