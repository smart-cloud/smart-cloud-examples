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
package org.smartframework.cloud.examples.support.gateway.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * 权限cache
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
@ToString
public class AuthCache implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户所拥有的角色
     */
    private Set<String> roles;
    /**
     * 用户所拥有的权限
     */
    private Set<String> permissions;

}