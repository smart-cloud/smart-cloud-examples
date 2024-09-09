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
package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import io.github.smart.cloud.mask.MaskLog;
import io.github.smart.cloud.mask.MaskRule;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * 登录（或注册）成功后缓存用户信息请求参数
 *
 * @author collin
 * @date 2020-09-11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CacheUserInfoReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    @NotBlank
    private String token;

    /**
     * 用户id
     */
    @NotNull
    private Long uid;

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 真实姓名
     */
    @NotBlank
    @MaskLog(MaskRule.NAME)
    private String realName;

    /**
     * 手机号
     */
    @MaskLog(MaskRule.MOBILE)
    private String mobile;

    /**
     * 用户所拥有的角色
     */
    private Set<String> roles;
    /**
     * 用户所拥有的权限
     */
    private Set<String> permissions;

}