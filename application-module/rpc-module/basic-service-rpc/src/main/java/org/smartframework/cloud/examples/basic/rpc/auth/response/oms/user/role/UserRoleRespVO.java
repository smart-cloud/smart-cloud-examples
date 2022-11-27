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
package org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * 角色权限信息
 *
 * @author collin
 * @date 2021-07-02
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@SuperBuilder
public class UserRoleRespVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色权限id
     */
    private Long relaId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 权限
     */
    private Set<PermissionInfoRespVO> permissons;

}