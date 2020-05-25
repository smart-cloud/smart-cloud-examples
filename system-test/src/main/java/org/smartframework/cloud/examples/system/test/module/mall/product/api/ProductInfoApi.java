package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import java.io.IOException;

import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.starter.core.business.util.ReqUtil;
import org.smartframework.cloud.utility.HttpUtil;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductInfoApi {

	public RespVO<BasePageRespVO<PageProductRespVO>> pageProduct() throws IOException {
		return HttpUtil.postWithRaw(
				SystemTestConfig.getProductBaseUrl() + "product/api/productInfo/pageProduct",
				ReqUtil.build(null, 1, 10), new TypeReference<RespVO<BasePageRespVO<PageProductRespVO>>>() {
				});
	}

}