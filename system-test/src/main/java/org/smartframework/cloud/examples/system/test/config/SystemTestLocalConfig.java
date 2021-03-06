package org.smartframework.cloud.examples.system.test.config;

public class SystemTestLocalConfig extends AbstractSystemTestConfig implements ISystemTestConfig {

    private static final boolean GATEWAY = true;
    private static final String GATEWAY_BASE_URL = "http://localhost:80/";

    @Override
    public String getUserBaseUrl() {
        return getBaseUrl("http://localhost:20031/");
    }

    @Override
    public String getOrderBaseUrl() {
        return getBaseUrl("http://localhost:20011/");
    }

    @Override
    public String getProductBaseUrl() {
        return getBaseUrl("http://localhost:20021/");
    }

    @Override
    public boolean isGateway() {
        return GATEWAY;
    }

    @Override
    public String getGatewayBaseUrl() {
        return GATEWAY_BASE_URL;
    }

}