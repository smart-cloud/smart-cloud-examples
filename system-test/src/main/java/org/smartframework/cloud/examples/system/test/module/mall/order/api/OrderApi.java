package org.smartframework.cloud.examples.system.test.module.mall.order.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.examples.system.test.util.HttpHeaderUtil;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

@UtilityClass
public class OrderApi {

    public RespVO<CreateOrderRespVO> create(CreateOrderReqVO createOrderReqVO) throws IOException {
        return HttpUtil.postWithRaw(SystemTestConfig.getOrderBaseUrl() + "order/api/order/create", HttpHeaderUtil.build(),
                createOrderReqVO, new TypeReference<RespVO<CreateOrderRespVO>>() {
                });
    }

}