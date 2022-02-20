/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.utility.HttpUtil;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.examples.system.test.util.HttpHeaderUtil;

import java.io.IOException;

@UtilityClass
public class ProductInfoApi {

    public Response<BasePageResponse<PageProductRespVO>> pageProduct(PageProductReqVO reqVO) throws IOException {
        return HttpUtil.get(
                SystemTestConfig.getProductBaseUrl() + "product/api/productInfo/pageProduct", HttpHeaderUtil.build(),
                reqVO, new TypeReference<Response<BasePageResponse<PageProductRespVO>>>() {
                });
    }

}