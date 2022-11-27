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
package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

import io.github.smart.cloud.mask.MaskLog;
import io.github.smart.cloud.mask.MaskRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 添加登陆信息请求参数
 *
 * @author collin
 * @date 2020-09-10
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Size(min = 6, max = 20)
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @Size(min = 6, max = 45)
    @NotBlank
    @MaskLog(MaskRule.PASSWROD)
    private String password;

    /**
     * 密码状态=={"1":"未设置","2":"已设置"}
     */
    @NotNull
    private Byte pwdState;

}