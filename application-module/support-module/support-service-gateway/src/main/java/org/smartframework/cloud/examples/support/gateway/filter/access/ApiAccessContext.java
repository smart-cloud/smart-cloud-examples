package org.smartframework.cloud.examples.support.gateway.filter.access;

/**
 * @author liyulin
 * @date 2020-09-08
 */
public final class ApiAccessContext {

    private static final ThreadLocal<ApiAccessBO> API_ACCESS_BO_THREAD_LOCAL = new ThreadLocal<>();

    public static ApiAccessBO getContext() {
        return API_ACCESS_BO_THREAD_LOCAL.get();
    }

    public static void setContext(ApiAccessBO apiAccessBO) {
        API_ACCESS_BO_THREAD_LOCAL.set(apiAccessBO);
    }

    public static void clear() {
        API_ACCESS_BO_THREAD_LOCAL.remove();
    }

}