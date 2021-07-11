package org.smartframework.cloud.examples.mall.rpc.constant;

public interface RpcConstants {

    interface Order {
        String SERVICE_NAME = "mallServiceOrder";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

    interface Product {
        String SERVICE_NAME = "mallServiceProduct";
        String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
    }

}