package org.smartframework.cloud.examples.support.gateway.constants;

/**
 * gateway常量
 *
 * @author liyulin
 * @date 2020-08-07
 */
public class GatewayConstants {

    /**
     * gateway接口url前缀
     */
    public static final String GATEWAY_API_URL_PREFIX = "/gateway";

    public static class AccessConstants {
        /**
         * http header timestamp
         */
        public static final String TIMESTAMP = "smart-timestamp";
        /**
         * http header nonce
         */
        public static final String NONCE = "smart-nonce";
        /**
         * http header token
         */
        public static final String TOKEN = "smart-token";
    }

}