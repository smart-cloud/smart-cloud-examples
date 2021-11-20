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
 * 密码状态=={"1":"未设置","2":"已设置"}
 *
 * @author collin
 * @date 2019-07-13
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PwdStateEnum {

    /**
     * 未设置
     */
    NOT_SETTING((byte) 1),
    /**
     * 已设置
     */
    DONE_SETTING((byte) 2);

    private byte value;

}