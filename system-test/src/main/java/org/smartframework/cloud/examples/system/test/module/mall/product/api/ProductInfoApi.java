package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import java.io.IOException;

import org.smartframework.cloud.common.pojo.dto.BasePageResp;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.api.PageProductRespBody;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.starter.common.business.util.ReqUtil;
import org.smartframework.cloud.utility.HttpUtil;

import com.alibaba.fastjson.TypeReference;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductInfoApi {

	public Resp<BasePageResp<PageProductRespBody>> pageProduct() throws IOException {
		return HttpUtil.postWithRaw(
				SystemTestConfig.getProductBaseUrl() + "api/identity/product/productInfo/pageProduct",
				ReqUtil.build(null, 1, 10), new TypeReference<Resp<BasePageResp<PageProductRespBody>>>() {
				});
	}

}