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
package org.smartframework.cloud.examples.mall.rpc.order.request.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 提交订单商品请求参数
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
public class SubmitOrderProductInfoReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @NotNull
    @Min(1)
    private Long productId;

    /**
     * 购买数量
     */
    @NotNull
    @Min(1)
    private Integer buyCount;

}