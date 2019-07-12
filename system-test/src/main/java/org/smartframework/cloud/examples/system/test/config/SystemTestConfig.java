package org.smartframework.cloud.examples.system.test.config;

import java.util.EnumMap;

import org.smartframework.cloud.examples.system.test.enums.SytemTestEnv;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemTestConfig {

	/** 当前环境 */
	private static final SytemTestEnv CURRENT_ENV = SytemTestEnv.LOCAL;

	private static final EnumMap<SytemTestEnv, AbstractSystemTestConfig> CONFIG_ROUTE = new EnumMap<>(
			SytemTestEnv.class);
	static {
		CONFIG_ROUTE.put(SytemTestEnv.LOCAL, new SystemTestLocalConfig());
	}

	public static String getOrderBaseUrl() {
		return CONFIG_ROUTE.get(CURRENT_ENV).getOrderBaseUrl();
	}
	
	public static String getProductBaseUrl() {
		return CONFIG_ROUTE.get(CURRENT_ENV).getProductBaseUrl();
	}

}