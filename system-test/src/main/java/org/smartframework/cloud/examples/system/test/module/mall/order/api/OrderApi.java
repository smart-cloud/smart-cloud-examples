package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.examples.system.test.util.HttpHeaderUtil;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

@UtilityClass
public class OrderApi {

    public Response<String> submit(SubmitOrderReqVO submitOrderReqVO) throws IOException {
        return HttpUtil.postWithRaw(SystemTestConfig.getOrderBaseUrl() + "order/api/order/submit", HttpHeaderUtil.build(),
                submitOrderReqVO, new TypeReference<Response<String>>() {
                });
    }

    public Response<QuerySubmitResultRespVO> querySubmitResult(String orderNo) throws IOException {
        return HttpUtil.get(SystemTestConfig.getOrderBaseUrl() + "order/api/order/submit?orderNo=" + orderNo, HttpHeaderUtil.build(),
                null, new TypeReference<Response<QuerySubmitResultRespVO>>() {
                });
    }

}