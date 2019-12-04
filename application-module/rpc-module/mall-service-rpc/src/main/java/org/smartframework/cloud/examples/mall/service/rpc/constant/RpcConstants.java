package org.smartframework.cloud.examples.mall.service.rpc.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpcConstants {

	public static final class Order {
		public static final String SERVICE_NAME = "mallServiceOrder";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

	public static final class Product {
		public static final String SERVICE_NAME = "mallServiceProduct";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

}