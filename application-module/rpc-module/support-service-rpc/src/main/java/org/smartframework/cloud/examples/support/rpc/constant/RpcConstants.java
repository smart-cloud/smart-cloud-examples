package org.smartframework.cloud.examples.support.rpc.constant;

public interface RpcConstants {

    interface Eureka {
        String SERVICE_NAME = "supportServiceEureka";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

    interface Gateway {
        String SERVICE_NAME = "supportServiceGateway";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

}