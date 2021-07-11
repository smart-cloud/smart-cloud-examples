package org.smartframework.cloud.examples.basic.rpc.constant;

public interface RpcConstants {

    interface User {
        String SERVICE_NAME = "basicServiceUser";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

    interface Auth {
        String SERVICE_NAME = "basicServiceAuth";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

}