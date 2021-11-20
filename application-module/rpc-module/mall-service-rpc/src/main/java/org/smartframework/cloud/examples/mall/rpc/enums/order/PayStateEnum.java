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
package org.smartframework.cloud.examples.mall.rpc.enums.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
 *
 * @author collin
 * @date 2019-04-16
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayStateEnum {

    /**
     * 待支付
     */
    PENDING_PAY((byte) 1),
    /**
     * 支付成功
     */
    PAY_SUCCESS((byte) 2),
    /**
     * 支付失败
     */
    PAY_FAIL((byte) 3),
    /**
     * 待退款
     */
    PENDING_REFUND((byte) 4),
    /**
     * 退款成功
     */
    PENDING_SUCCESS((byte) 5),
    /**
     * 退款失败
     */
    PENDING_FAIL((byte) 6);

    private byte value;

}