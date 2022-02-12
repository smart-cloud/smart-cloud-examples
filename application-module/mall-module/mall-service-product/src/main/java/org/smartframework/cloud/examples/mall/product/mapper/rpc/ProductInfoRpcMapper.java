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
package org.smartframework.cloud.examples.mall.product.mapper.rpc;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;

/**
 * 商品rpc mapper
 *
 * @author collin
 * @date 2019-04-07
 */
@DS(DataSourceName.MALL_PRODUCT_MASTER)
@Mapper
public interface ProductInfoRpcMapper {

	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	int updateStock(@Param("list") List<UpdateStockItem> list);

}