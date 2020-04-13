package org.smartframework.cloud.examples.support.rpc.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpcConstants {

	public static final class Eureka {
		public static final String SERVICE_NAME = "supportServiceEureka";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

	public static final class Gateway {
		public static final String SERVICE_NAME = "supportServiceGateway";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

}