package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.examples.system.test.util.HttpHeaderUtil;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

/**
 * @author liyulin
 * @date 2020-10-10
 */
@UtilityClass
public class ProductInfoOms {


    public Response<Base> create(ProductInsertReqVO reqVO) throws IOException {
        return HttpUtil.postWithRaw(
                SystemTestConfig.getProductBaseUrl() + "product/oms/productInfo/create", HttpHeaderUtil.build(),
                reqVO, new TypeReference<Response<Base>>() {
                });
    }

}