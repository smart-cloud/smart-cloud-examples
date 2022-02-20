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
package org.smartframework.cloud.examples.mall.rpc.product.request.oms;

import io.github.smart.cloud.common.pojo.Base;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 商品新增请求参数
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
public class ProductInsertReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @Size(max = 100)
    @NotBlank
    private String name;

    /**
     * 销售价格（单位：万分之一元）
     */
    @Min(100)
    @NotNull
    private Long sellPrice;

    /**
     * 库存
     */
    @Min(1)
    @NotNull
    private Long stock;

}