package org.smartframework.cloud.examples.system.test.config;

public abstract class AbstractSystemTestConfig {

    /**
     * 获取base url
     *
     * @param singleBaseUrl 单体服务base url
     * @return
     */
    protected String getBaseUrl(String singleBaseUrl) {
        if (isGateway()) {
            return getGatewayBaseUrl();
        } else if (isMerge()) {
            return getMergeBaseUrl();
        } else {
            return singleBaseUrl;
        }
    }

    /**
     * 是否走网关
     *
     * @return
     */
    public abstract boolean isGateway();

    /**
     * 网关地址前缀
     *
     * @return
     */
    public abstract String getGatewayBaseUrl();

    /**
     * 是否走合并服务
     *
     * @return
     */
    public abstract boolean isMerge();

    /**
     * 合并服务地址前缀
     *
     * @return
     */
    public abstract String getMergeBaseUrl();

}