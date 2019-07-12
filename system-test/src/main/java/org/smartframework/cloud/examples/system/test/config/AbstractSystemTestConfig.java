package org.smartframework.cloud.examples.system.test.config;

public abstract class AbstractSystemTestConfig {

	public abstract String getOrderBaseUrl();
	
	public abstract String getProductBaseUrl();

	/**
	 * 获取base url
	 * 
	 * @param merge         是否是合体服务
	 * @param mergeBaseUrl  合体服务base url
	 * @param singleBaseUrl 单体服务base url
	 * @return
	 */
	protected String getBaseUrl(boolean merge, String mergeBaseUrl, String singleBaseUrl) {
		return merge ? mergeBaseUrl : singleBaseUrl;
	}

}