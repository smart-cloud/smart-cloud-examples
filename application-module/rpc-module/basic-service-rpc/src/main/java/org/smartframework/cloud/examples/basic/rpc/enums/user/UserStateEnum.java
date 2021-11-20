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
package org.smartframework.cloud.examples.basic.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态=={"1":"启用","2":"禁用"}
 *
 * @author collin
 * @date 2019-06-29
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserStateEnum {

    /**
     * 启用
     */
    ENABLE((byte) 1),
    /**
     * 禁用
     */
    UNENABLE((byte) 2);

    private byte value;

}