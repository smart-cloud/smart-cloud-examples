package org.smartframework.cloud.examples.basic.rpc.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpcConstants {

	public static final class User {
		public static final String SERVICE_NAME = "basicServiceUser";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

	public static final class Auth {
		public static final String SERVICE_NAME = "basicServiceAuth";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

}