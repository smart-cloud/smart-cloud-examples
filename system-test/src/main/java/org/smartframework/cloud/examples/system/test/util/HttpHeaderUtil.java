package org.smartframework.cloud.examples.system.test.util;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.UUID;

/**
 * http header工具类
 *
 * @author liyulin
 * @date 2020-10-10
 */
public class HttpHeaderUtil {

    public static Header[] build() {
        Header[] headers = new Header[3];
        headers[0] = new BasicHeader("smart-timestamp", String.valueOf(System.currentTimeMillis()));
        headers[1] = new BasicHeader("smart-nonce", UUID.randomUUID().toString());
        headers[2] = new BasicHeader("smart-token", TokenUtil.getContext().getToken());
        return headers;
    }

}